package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product createNew(Product requestProduct);

    List<Product> createNewBulk(List<Product> products);

    Page<Product> findAll(SearchProductRequest request);

    Product findById(String requestId);

    String deleteById(String requestId);

    Product updateById(Product requestProduct);

}
