package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query(value = "select p from Product p where p.name like :name " +
                        "and p.price >= :minPrice " +
                             "and p.price <= :maxPrice")
    List<Product> findByNameorMinMaxPrice(@Param("name") String name,
                                            @Param("minPrice") Integer minPrice,
                                                @Param("maxPrice") Integer maxPrice);
}
