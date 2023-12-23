package com.enigma.tokopakedi.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse{
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
}
