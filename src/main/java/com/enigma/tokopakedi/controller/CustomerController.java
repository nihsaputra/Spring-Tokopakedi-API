package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(path = "/customers")
    public Customer createNewCustomer(@RequestBody Customer request){

        Customer newCustomer = customerRepository.save(request);

        return newCustomer;
    }

    @GetMapping(path = "/customers")
    public List<Customer> findAllCustomers(){

        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    @GetMapping(path = "/customers/id")
    public Customer findCustomerById(@RequestBody Customer customerId){

        Customer customer = customerRepository.findById(customerId.getId())
                .orElse(null);

        return customer;
    }

    @DeleteMapping(path = "/customers")
    public String deleteById(@RequestBody Customer request){

        customerRepository.deleteById(request.getId());

        return "Delete Success";
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer request){

        Customer customer = customerRepository.findById(request.getId()).orElse(null);

        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);

        return customer;
    }


}
