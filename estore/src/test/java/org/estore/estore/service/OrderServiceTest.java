package org.estore.estore.service;

import org.estore.estore.dto.request.CreateOrderRequest;
import org.estore.estore.dto.request.ItemRequest;
import org.estore.estore.dto.request.UpdateOrderRequest;
import org.estore.estore.dto.response.CreateOrderResponse;
import org.estore.estore.dto.response.DeleteOrderResponse;
import org.estore.estore.dto.response.GetOrderResponse;
import org.estore.estore.dto.response.UpdateOrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.estore.estore.utils.TestUtils.buildCreateOrder;
import static org.estore.estore.utils.TestUtils.buildUpdateOrderRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @Sql(scripts = {"/db/script.sql"})
    public void testCanPlaceOrder() {
        CreateOrderResponse createOrderResponse = orderService.create(buildCreateOrder());
        assertNotNull(createOrderResponse);
        assertThat(createOrderResponse.getMessage())
                .containsIgnoringCase("Order Placed Successfully");

    }


    @Test
    @Sql(scripts = {"/db/script.sql"})
    public void testCanGetOrderById() {
        CreateOrderResponse createResponse = orderService.create(buildCreateOrder());
        String orderId = createResponse.getOrderId();

        GetOrderResponse getResponse = orderService.getById(orderId);

        assertNotNull(getResponse);
        assertThat(getResponse.getOrderId()).isEqualTo(orderId);
        assertThat(getResponse.getOrderStatus()).isNotNull();
        assertThat(getResponse.getItems()).isNotEmpty();
    }

    @Test
    @Sql(scripts = {"/db/script.sql"})
    public void testCanUpdateOrder() {
        CreateOrderResponse createResponse = orderService.create(buildCreateOrder());
        String orderId = createResponse.getOrderId();

        UpdateOrderRequest updateRequest = buildUpdateOrderRequest(orderId);
        UpdateOrderResponse updateResponse = orderService.update(updateRequest);

        assertNotNull(updateResponse);
        assertThat(updateResponse.getOrderId()).isEqualTo(orderId);
        assertThat(updateResponse.getMessage()).containsIgnoringCase("Order updated successfully");
    }

    @Test
    @Sql(scripts = {"/db/script.sql"})
    public void testCanDeleteOrder() {
        CreateOrderResponse createResponse = orderService.create(buildCreateOrder());
        String orderId = createResponse.getOrderId();

        DeleteOrderResponse deleteResponse = orderService.deleteById(orderId);

        assertNotNull(deleteResponse);
        assertThat(deleteResponse.getMessage()).containsIgnoringCase("Order deleted successfully");
        assertThat(deleteResponse.getOrderId()).isEqualTo(orderId);
    }
}
