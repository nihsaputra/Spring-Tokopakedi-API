package com.enigma.tokopakedi.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDetailRequest {

    private String productId;
    private Integer quantity;

}
