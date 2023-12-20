package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping(path = "/products")
    public Product createNewProduct(@RequestBody Product request){

        Product newProduct = productRepository.save(request);

        return newProduct;
    }

    @GetMapping(path = "/products")
    public List<Product> findAllProducts(){

        List<Product> allProduct = productRepository.findAll();

        return allProduct;
    }

    @GetMapping(path = "/products/id")
    public Product findProductId(@RequestBody Product request){

        Product productId = productRepository.findById(request.getId()).orElse(null);

        return productId;

    }

    @DeleteMapping(path = "/products")
    public String deleteProductId(@RequestBody Product request){

        Product productId = productRepository.findById(request.getId()).orElse(null);

        productRepository.deleteById(productId.getId());

        return "Delete Success";

    }

    @PutMapping(path = "products")
    public Product updateProductId(@RequestBody Product request){

        Product product = productRepository.findById(request.getId())
                .orElse(null);

        product.setName(request.getName());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        productRepository.save(product);

        return product;
    }
}
