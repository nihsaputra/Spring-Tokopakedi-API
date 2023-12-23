package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<WebResponse<Customer>> createNewCustomer(@RequestBody Customer customer){

        Customer createCustomer = customerService.createNew(customer);

        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("create new customer successfuly")
                .data(createCustomer)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/customers/bulk")
    public ResponseEntity<WebResponse<List<Customer>>> createBulkCustomers(@RequestBody List<Customer> customers){

        List<Customer> createCustomer = customerService.createBulk(customers);

        WebResponse<List<Customer>> response = WebResponse.<List<Customer>>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfuly create bulk customers")
                .data(createCustomer)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/customers/{requestId}")
    public ResponseEntity<WebResponse<Customer>> findCustomerById(@PathVariable String requestId){

        Customer findCustomerById = customerService.findById(requestId);

        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("succcessfuly find customer by id")
                .data(findCustomerById)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/customers")
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

    @PutMapping(path = "/customers")
    public ResponseEntity<WebResponse<Customer>> updateCustomer(@RequestBody Customer requestCustomer){

        Customer updateCustomer = customerService.updateById(requestCustomer);

        WebResponse<Customer> response = WebResponse.<Customer>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly update customer")
                .data(updateCustomer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/customers/{requestId}")
    public ResponseEntity<WebResponse<String>> deleteCustomer(@PathVariable String requestId){

        customerService.deleteById(requestId);

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfuly delete customer")
                .build();

        return ResponseEntity.ok(response);
    }

}
