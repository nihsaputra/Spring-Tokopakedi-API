package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @PostMapping(path = "/products")
    public Product createNewProduct(@RequestBody Product requestProduct){
        return productService.createNew(requestProduct);
    }

//    @GetMapping(path = "/products")
//    public List<Product> findAllProducts(){
//        return productService.findAll();
//    }

    @GetMapping(path = "/products/{requestId}")
    public Product getProductIdPath(@PathVariable String requestId){
        return productService.findById(requestId);
    }


    @DeleteMapping(path = "/products/{requestId}")
    public String deleteProductPath(@PathVariable String requestId){
        return productService.deleteById(requestId);
    }

    @PutMapping(path = "products")
    public Product updateProductRequest(@RequestBody Product requestProduct){
            return productService.updateById(requestProduct);
    }

    @GetMapping(path = "/products/{page}/{size}")
    public List<Product> findALlPageAndSize(@PathVariable("page") int page, @PathVariable("size") int size){
        Pageable pageable= PageRequest.of(page,size);

        Page<Product> findAllPageAndSize = productRepository.findAll(pageable);

        return findAllPageAndSize.getContent();
    }

    // localhost:8081/products?name=&minPrice=0&maxPrice=1000000
    @GetMapping(path = "/products")
    public List<Product> findByNameOrMaxMinPrice(@RequestParam String name,
                                                    @RequestParam(name = "minPrice", defaultValue = "0") Integer minPrice,
                                                        @RequestParam(name = "maxPrice", defaultValue = "1000000") Integer maxPrice){

        return productService.findByNameOrMaxMinPrice(name, minPrice, maxPrice);
    }
}
