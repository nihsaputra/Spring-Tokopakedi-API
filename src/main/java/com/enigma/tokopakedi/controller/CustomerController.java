package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Customer createNewCustomer(@RequestBody Customer requestCustomer){
        return customerService.createNew(requestCustomer);
    }

//    @GetMapping(path = "/customers")
//    public List<Customer> findAllCustomers(){
//        return customerService.findAll();
//    }

    @GetMapping(path = "/customers/{requestId}")
    public Customer findCustomerById(@PathVariable String requestId){
        return customerService.findById(requestId);
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer requestCustomer){
        return customerService.updateById(requestCustomer);
    }

    @DeleteMapping(path = "/customers/{requestId}")
    public String deleteCustomer(@PathVariable String requestId){

        customerService.deleteById(requestId);

        return "OK";
    }

    @GetMapping(path = "/customers/{page}/{size}")
    public List<Customer> findAllPageAndSize(@PathVariable("page") int page,@PathVariable("size") int size){
        return customerService.findAllPageAndSize(page, size);
    }

    @GetMapping(path = "/customers")
    public List<Customer> findByNameOrPhone(@RequestParam String search){
        return customerService.searchCustomerByNameOrPhone(search);
    }

}
