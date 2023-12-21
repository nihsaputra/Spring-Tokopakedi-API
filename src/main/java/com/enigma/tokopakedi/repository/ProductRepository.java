package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query(value = "select p from Product p where p.name like :search ")
    List<Product> findByNameorMinMaxPrice(@Param("search") String search);
}
