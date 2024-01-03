package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(path = "/{requestId}")
    public ResponseEntity<WebResponse<Customer>> findCustomerById(@PathVariable String requestId){

        Customer findCustomerById = customerService.findById(requestId);

        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("succcessfuly find customer by id")
                .data(findCustomerById)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<WebResponse<List<Customer>>> findAllCustomers(
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String phoneNumber){

        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();

        Page<Customer> findAllCustomer = customerService.findAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(findAllCustomer.getTotalPages())
                .totalElements(findAllCustomer.getTotalElements())
                .build();

        WebResponse<List<Customer>> response = WebResponse.<List<Customer>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly find all customer")
                .paging(pagingResponse)
                .data(findAllCustomer.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<WebResponse<Customer>> updateCustomer(@RequestBody Customer requestCustomer){

        Customer updateCustomer = customerService.updateById(requestCustomer);

        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly update customer")
                .data(updateCustomer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{requestId}")
    public ResponseEntity<WebResponse<String>> deleteCustomer(@PathVariable String requestId){

        customerService.deleteById(requestId);

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly delete customer")
                .build();

        return ResponseEntity.ok(response);
    }

}
