package com.enigma.tokopakedi.model;


import com.enigma.tokopakedi.entity.Product;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private String id;
    private String orderId;
    private Product product;
    private Long productPrice;
    private Integer quantity;

}
