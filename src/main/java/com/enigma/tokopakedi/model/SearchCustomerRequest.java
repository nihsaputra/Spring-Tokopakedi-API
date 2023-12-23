package com.enigma.tokopakedi.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SearchCustomerRequest {

    private Integer page;

    private Integer size;

    private String name;

    private String phoneNumber;

}
