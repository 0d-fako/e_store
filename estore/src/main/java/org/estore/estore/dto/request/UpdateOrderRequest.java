package org.estore.estore.dto.request;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateOrderRequest {

    private String orderId;
    private String orderStatus;
    private String notes;
}
