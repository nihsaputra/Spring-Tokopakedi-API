package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createNew(Customer requestCustomer) {

        Customer newCustomer = customerRepository.save(requestCustomer);

        return newCustomer;
    }

    @Override
    public List<Customer> findAll() {

        List<Customer> customers = customerRepository.findAll();

        return customers;
    }

    @Override
    public Customer findById(String requestId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(requestId);

        if (optionalCustomer.isPresent()) return optionalCustomer.get();

        throw new RuntimeException("customer not found");
    }

    @Override
    public Customer updateById(Customer requestCustomer) {

        // Mencari id dari parameter
        Optional<Customer> optionalCustomer = customerRepository.findById(requestCustomer.getId());

        // Mengecek apakah id nya ketemu, jika tidak ada maka terjadi throw
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("customer not found");
        }

        // MengUpdate data berdasarkan data yang dikirim parameter, jika tidak terjadi throw
        Customer updateCustomer = customerRepository.save(requestCustomer);

        return updateCustomer;
    }

    @Override
    public void deleteById(String requestId) {
        // Mencari id dari parameter
        Optional<Customer> optionalCustomer = customerRepository.findById(requestId);

        // Mengecek apakah id nya ketemu, jika tidak ada maka throw
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("customer not found");
        }

        // meng delete id, jika tidak terjadi throw
        Customer customer= optionalCustomer.get();
        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> findAllPageAndSize(int page,int size) {

        Pageable pageable= PageRequest.of(page,size);

        Page<Customer> findAllPageAndSize = customerRepository.findAll(pageable);

        return findAllPageAndSize.getContent();
    }

    @Override
    public List<Customer> searchCustomerByNameOrPhone(String search) {
        String tempt= "%"+ search +"%";
        List<Customer> searchCustomers = customerRepository.searchCustomerByNameOrPhone(tempt);

        return searchCustomers;
    }


}
