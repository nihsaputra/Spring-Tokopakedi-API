package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request){
        OrderResponse order = orderService.createTransaction(request);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfuly create new transaction")
                .data(order)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "10", required = false) Integer size){
        SearchOrderRequest request = SearchOrderRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Order> findById = orderService.findAll(request);

        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : findById) {
            OrderResponse build = OrderResponse.builder()
                    .id(order.getId())
                    .customerId(order.getCustomer().getId())
                    .transDate(order.getTransDate())
                    .orderDetail(order.getOrderDetails())
                    .build();
            orderResponses.add(build);
        }

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(findById.getTotalPages())
                .totalElements(findById.getTotalElements())
                .build();

        WebResponse<List<OrderResponse>> response = WebResponse.<List<OrderResponse>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly find all orders")
                .paging(pagingResponse)
                .data(orderResponses)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") String id){
        OrderResponse findById = orderService.findById(id);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly find by id orders")
                .data(findById)
                .build();
        return ResponseEntity.ok(response);
    }

}
