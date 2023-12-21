package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;

import java.util.List;

public interface ProductService {

    Product createNew(Product requestProduct);

    List<Product> findAll();

    Product findById(String requestId);

    String deleteById(String requestId);

    Product updateById(Product requestProduct);

    List<Product> findByNameOrMaxMinPrice(String name, Integer minPrice, Integer maxPrice);
}
