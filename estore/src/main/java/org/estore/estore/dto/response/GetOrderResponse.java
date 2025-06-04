package org.estore.estore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetOrderResponse {
    private String orderId;
    private String cartId;
    private String orderStatus;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;
}