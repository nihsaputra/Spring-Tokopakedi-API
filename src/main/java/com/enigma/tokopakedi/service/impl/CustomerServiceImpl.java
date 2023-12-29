package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer createNew(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer;
    }

    @Override
    public List<Customer> createBulk(List<Customer> customers) {
        List<Customer> createBulkCustomer = customerRepository.saveAll(customers);

        return createBulkCustomer;
    }

    @Override
    public Page<Customer> findAll(SearchCustomerRequest request) {

        if (request.getPage()<=0){
            request.setPage(1);
        }

        if (request.getSize()<=0){
            request.setSize(10);
        }

        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());

        // spesification where name like :name or phoneNumber == :phoneNumber
        Specification<Customer> spesificationCustomer = getSpesificationCustomer(request);

        return customerRepository.findAll(spesificationCustomer,pageable);

    }

    @Override
    public Customer findById(String requestId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(requestId);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new RuntimeException("customer not found");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer updateById(Customer requestCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(requestCustomer.getId());
        if (optionalCustomer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found");
        }

        // user yang saat ini login membawa token
        UserCredential currenUserCredential = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCredential credential = optionalCustomer.get().getUserCredential();
        if (!currenUserCredential.getId().equals(credential.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"forbidden");
        }

        requestCustomer.setUserCredential(credential);
        Customer updateCustomer = customerRepository.save(requestCustomer);
        return updateCustomer;
    }

    @Override
    public void deleteById(String requestId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(requestId);
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("customer not found");
        }
        Customer customer= optionalCustomer.get();
        customerRepository.delete(customer);
    }

    private static Specification<Customer> getSpesificationCustomer(SearchCustomerRequest request) {
        Specification<Customer> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%");
                predicates.add(namePredicate);
            }

            if (request.getPhoneNumber() != null){
                Predicate phoneNumberPredicate = criteriaBuilder.equal(root.get("phoneNumber"), request.getPhoneNumber());
                predicates.add(phoneNumberPredicate);
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return specification;
    }

}
