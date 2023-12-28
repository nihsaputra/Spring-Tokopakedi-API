package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.repository.OrderDetailRepository;
import com.enigma.tokopakedi.repository.OrderRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderDetailService;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createTransaction(OrderRequest request) {
        Customer customer= customerService.findById(request.getCustomerId());
        Order order = Order.builder()
                .customer(customer)
                .transDate(new Date())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Product product = productService.findById(orderDetailRequest.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - orderDetail.getQuantity() < 0 ){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"quantity exceeds");
            }

            product.setStock(product.getStock() - orderDetail.getQuantity());
            productService.updateById(product);

            orderDetailService.create(orderDetail);

            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .id(orderDetail.getId())
                    .orderId(order.getId())
                    .product(orderDetail.getProduct())
                    .productPrice(orderDetail.getProductPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();

            orderDetails.add(orderDetailResponse); //tampung diArayList
        }

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .transDate(order.getTransDate())
                .orderDetail(orderDetails)
                .build();

        return orderResponse;
    }

    @Override
    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));

        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .transDate(order.getTransDate())
                .orderDetail(order.getOrderDetails())
                .build();

        return orderResponse;
    }

    @Override
    public Page<Order> findAll(SearchOrderRequest request) {
        if (request.getPage() <= 0){
            request.setPage(1);
        }

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Page<Order> findAll = orderRepository.findAll(pageable);

        return findAll;
    }




//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Order createTransaction(OrderRequest request) {
//        Customer customer= customerService.findById(request.getCustomerId());
//        Order order = Order.builder()
//                .customer(customer)
//                .transDate(new Date())
//                .build();
//        orderRepository.saveAndFlush(order);
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//
//        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
//            Product product = productService.findById(orderDetailRequest.getProductId());
//
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .order(order)
//                    .product(product)
//                    .productPrice(product.getPrice())
//                    .quantity(orderDetailRequest.getQuantity())
//                    .build();
//
//            if (product.getStock() - orderDetail.getQuantity() < 0 ){
//                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"quantity exceeds");
//            }
//
//            product.setStock(product.getStock() - orderDetail.getQuantity());
//
//            orderDetails.add(orderDetail);
//        }
//        order.setOrderDetails(orderDetails);
//        return order;
//    }
}
