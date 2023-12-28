package com.enigma.tokopakedi.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderRequest {

    private String customerId;
    private List<OrderDetailRequest> orderDetails;

}
