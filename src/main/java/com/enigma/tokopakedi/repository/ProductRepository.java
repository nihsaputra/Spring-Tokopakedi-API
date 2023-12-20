package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
}
