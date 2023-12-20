package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
