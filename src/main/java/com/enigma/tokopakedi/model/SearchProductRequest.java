package com.enigma.tokopakedi.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {

    private Integer page;

    private Integer size;

    private String name;

    private Long minPrice;

    private Long maxPirce;

}
