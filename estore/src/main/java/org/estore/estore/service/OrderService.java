package org.estore.estore.service;

import org.estore.estore.dto.request.CreateOrderRequest;
import org.estore.estore.dto.response.CreateOrderResponse;
import org.estore.estore.dto.response.DeleteOrderResponse;
import org.estore.estore.dto.response.UpdateOrderResponse;
import org.estore.estore.dto.response.GetOrderResponse;
import org.springframework.data.domain.Page;
import org.estore.estore.dto.request.UpdateOrderRequest;

import java.util.List;


public interface OrderService {
    CreateOrderResponse create(CreateOrderRequest order);

    GetOrderResponse getById(String orderId);

    UpdateOrderResponse update(UpdateOrderRequest updateRequest);

    DeleteOrderResponse deleteById(String orderId);
}