package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/products/{requestId}")
    public Product getProductIdPath(@PathVariable String requestId){

        Product product = productRepository.findById(requestId).orElse(null);

        return product;

    }


    @DeleteMapping(path = "/products/{requestId}")
    public String deleteProductPath(@PathVariable String requestId){

        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestId);

        // Pengecekan jika id nya tidak ada maka akan throw
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found");

        // Menghapus berdasarkan id, jika tidak terkena throw
        Product product = optionalProduct.get();
        productRepository.delete(product);

        return "OK";

    }

    @PutMapping(path = "products")
    public Product updateProductRequest(@RequestBody Product request){

        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(request.getId());

        // Mengecek jika id nya tidak ada maka akan throw
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found");

        // Mengupdate berdasarkan id, jika tidak terkena throw
        Product updateProduct = productRepository.save(request);

        return updateProduct;
    }

}
