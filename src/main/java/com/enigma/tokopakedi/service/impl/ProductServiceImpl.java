package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createNew(Product requestProduct) {
        Product newProduct = productRepository.save(requestProduct);

        return newProduct;
    }

    @Override
    public List<Product> findAll() {
        List<Product> allProduct = productRepository.findAll();

        return allProduct;
    }

    @Override
    public Product findById(String requestId) {
        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestId);

        // Pengecekan jika id nya tidak di temukan maka akan throw
        if (optionalProduct.isEmpty()){
            throw new RuntimeException("product not found");
        }

        // mengirimkan hasil findById
        return optionalProduct.get();
    }

    @Override
    public String deleteById(String requestId) {
        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestId);

        // Pengecekan jika id nya tidak ditemukan maka akan throw
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found");

        // Menghapus berdasarkan id, jika tidak terkena throw
        Product product = optionalProduct.get();
        productRepository.delete(product);

        return "OK";
    }

    @Override
    public Product updateById(Product requestProduct) {

        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestProduct.getId());

        // Mengecek jika id nya tidak ada maka akan throw
        if (optionalProduct.isEmpty()) throw new RuntimeException("Product not found");

        // Mengupdate berdasarkan id, jika tidak terkena throw
        Product updateProduct = productRepository.save(requestProduct);

        return updateProduct;
    }

    @Override
    public List<Product> findByNameOrMaxMinPrice(String name, Integer minPrice, Integer maxPrice) {

        String tempName= "%" + name + "%";

        return productRepository.findByNameorMinMaxPrice(tempName, minPrice, maxPrice);
    }


}
