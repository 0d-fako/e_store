package org.estore.estore.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteOrderResponse {
    private String orderId;
    private String message;
}