package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {
    Customer createNew(Customer customer);

    List<Customer> createBulk(List<Customer> customers);

    Page<Customer> findAll(SearchCustomerRequest request);

    Customer findById(String requestId);

    Customer updateById(Customer requestCustomer);

    void deleteById(String requestId);

}
