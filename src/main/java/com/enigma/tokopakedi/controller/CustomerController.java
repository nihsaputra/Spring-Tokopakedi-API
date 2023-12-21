package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
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
    public Customer createNewCustomer(@RequestBody Customer request){

        Customer newCustomer = customerRepository.save(request);

        return newCustomer;
    }

    @GetMapping(path = "/customers")
    public List<Customer> findAllCustomers(){

        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    @GetMapping(path = "/customers/{requestId}")
    public Customer findCustomerById(@PathVariable String requestId){

        Optional<Customer> optionalCustomer = customerRepository.findById(requestId);

        if (optionalCustomer.isPresent()) return optionalCustomer.get();

        throw new RuntimeException("customer not found");
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer request){

        Optional<Customer> optionalCustomer = customerRepository.findById(request.getId());

        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("customer not found");
        }

        Customer updateCustomer = customerRepository.save(request);

        return updateCustomer;
    }

    @DeleteMapping(path = "/customers/{id}")
    public String deleteCustomer(@PathVariable String id){

        customerService.deleteById(id);

        return "OK";
    }

}
