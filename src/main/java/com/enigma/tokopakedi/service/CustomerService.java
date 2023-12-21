package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {
    Customer createNew(Customer requestCustomer);

    List<Customer> findAll();

    Customer findById(String requestId);

    Customer updateById(Customer requestCustomer);

    void deleteById(String requestId);

    List<Customer> findAllPageAndSize(int page,int size);

    List<Customer> searchCustomerByNameOrPhone(String search);
}
