package org.estore.estore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class AddProductResponse {

    private String name;
    private String description;
    private BigDecimal price;

}
