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
    public Product getProductIdRequest(@RequestBody Product request){

        Product productId = productRepository.findById(request.getId()).orElse(null);

        return productId;

    }

    @GetMapping(path = "/products/{requestId}")
    public Product getProductIdPath(@PathVariable String requestId){

        Product product = productRepository.findById(requestId).orElse(null);

        return product;

    }

    @DeleteMapping(path = "/products")
    public String deleteProductRequest(@RequestBody Product request){

        Product productId = productRepository.findById(request.getId()).orElse(null);

        productRepository.deleteById(productId.getId());

        return "Delete Success";

    }

    @DeleteMapping(path = "/products/{requestId}")
    public String deleteProductPath(@PathVariable String requestId){

        productRepository.deleteById(requestId);

        return "Delete Success";

    }

    @PutMapping(path = "products/id")
    public Product updateProductRequest(@RequestBody Product request){

        Product product = productRepository.findById(request.getId())
                .orElse(null);

        product.setName(request.getName());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        productRepository.save(product);

        return product;
    }

    @PutMapping(path = "products/{requestId}")
    public Product updateProductPath(@PathVariable String requestId){

        Product request = productRepository.findById(requestId).orElse(null);

        Product product= new Product();
        product.setId(requestId);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updateProduct = productRepository.save(product);

        return updateProduct;
    }
}
