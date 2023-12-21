package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "select c from Customer c where c.name like :search or c.phoneNumber like :search" )
    List<Customer> searchCustomerByNameOrPhone(@Param("search") String search);
}
