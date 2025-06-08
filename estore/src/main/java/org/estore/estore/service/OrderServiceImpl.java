package org.estore.estore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.estore.estore.dto.request.CreateOrderRequest;
import org.estore.estore.dto.request.ItemRequest;
import org.estore.estore.dto.request.UpdateOrderRequest;
import org.estore.estore.dto.response.*;
import org.estore.estore.exception.ItemOutOfStockException;
import org.estore.estore.exception.OrderNotFoundException;
import org.estore.estore.model.Item;
import org.estore.estore.model.Order;
import org.estore.estore.model.Product;
import org.estore.estore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    @Override
    public CreateOrderResponse create(CreateOrderRequest order) {
        order.getItems().forEach(this::checkAvailabilityOf);
        return buildCreateOrderResponseFrom(orderRepository.save(new Order(order)));
    }
    @Override
    public GetOrderResponse getById(String orderId) {
        log.info("Getting order by ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        return buildGetOrderResponseFrom(order);
    }

    @Override
    public UpdateOrderResponse update(UpdateOrderRequest updateRequest) {
        log.info("Updating order with ID: {}", updateRequest.getOrderId());

        Order existingOrder = orderRepository.findById(updateRequest.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + updateRequest.getOrderId()));

        updateOrderFields(existingOrder, updateRequest);
        Order updatedOrder = orderRepository.save(existingOrder);

        log.info("Order updated successfully with ID: {}", updatedOrder.getId());
        return buildUpdateOrderResponseFrom(updatedOrder);
    }

    @Override
    public DeleteOrderResponse deleteById(String orderId) {
        log.info("Deleting order with ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        orderRepository.delete(order);

        log.info("Order deleted successfully with ID: {}", orderId);
        return buildDeleteOrderResponseFrom(orderId);
    }

    private static CreateOrderResponse buildCreateOrderResponseFrom(Order customerOrder) {
        var orderResponse =  new CreateOrderResponse();
        orderResponse.setOrderStatus("IN_PROGRESS");
        orderResponse.setOrderId(customerOrder.getId());
        orderResponse.setMessage("Order placed Successfully");
        return orderResponse;
    }

    private void checkAvailabilityOf(ItemRequest item) {
        Item foundItem = itemService.getItemBy(item.getId());
        Product product = productService.getProductBy(foundItem.getProductId());
        if (item.getQuantity() > product.getQuantity())
            throw new ItemOutOfStockException("item out of stock");
    }

    private GetOrderResponse buildGetOrderResponseFrom(Order order) {
        var orderResponse = new GetOrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setCartId(order.getCartId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        orderResponse.setNotes(order.getNotes());

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::buildOrderItemResponseFrom)
                .collect(Collectors.toList());
        orderResponse.setItems(itemResponses);

        return orderResponse;
    }

    private static UpdateOrderResponse buildUpdateOrderResponseFrom(Order order) {
        var updateResponse = new UpdateOrderResponse();
        updateResponse.setOrderId(order.getId());
        updateResponse.setOrderStatus(order.getOrderStatus());
        updateResponse.setMessage("Order updated successfully");
        updateResponse.setUpdatedAt(LocalDateTime.now());
        return updateResponse;
    }

    private static DeleteOrderResponse buildDeleteOrderResponseFrom(String orderId) {
        var deleteResponse = new DeleteOrderResponse();
        deleteResponse.setOrderId(orderId);
        deleteResponse.setMessage("Order deleted successfully");
        return deleteResponse;
    }

    private OrderItemResponse buildOrderItemResponseFrom(Item item) {
        var itemResponse = new OrderItemResponse();
        itemResponse.setItemId(item.getId());
        itemResponse.setProductId(item.getProductId());
        itemResponse.setQuantity(item.getQuantity());

        // Get product details
        Product product = productService.getProductBy(item.getProductId());
        itemResponse.setProductName(product.getName());
        itemResponse.setUnitPrice(product.getPrice());
        itemResponse.setTotalPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity())));

        return itemResponse;
    }

    private void updateOrderFields(Order existingOrder, UpdateOrderRequest updateRequest) {
        if (updateRequest.getOrderStatus() != null) {
            existingOrder.setOrderStatus(updateRequest.getOrderStatus());
        }
        if (updateRequest.getNotes() != null) {
            existingOrder.setNotes(updateRequest.getNotes());
        }
        existingOrder.setUpdatedAt(LocalDateTime.now());
    }



}
