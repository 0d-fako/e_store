package org.estore.estore.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateOrderResponse {
    private String orderId;
    private String orderStatus;
    private String message;
    private LocalDateTime updatedAt;
}