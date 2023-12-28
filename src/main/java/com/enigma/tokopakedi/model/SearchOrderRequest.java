package com.enigma.tokopakedi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchOrderRequest {

    private Integer page;
    private Integer size;

}
