package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public List<Product> createNewBulk(List<Product> products) {
       return productRepository.saveAll(products);
    }

    @Override
    public Page<Product> findAll(SearchProductRequest request) {

        // jika page <= 0 maka page akan di ubah menjadi page=1
        if (request.getPage() <=0 ){
            request.setPage(1);
        }

        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());

        //spesification where name like :name or price >= :minPrice or price <= :maxPrice
        Specification<Product> specification = getProductSpecification(request);

        return productRepository.findAll(specification, pageable);
    }

    @Override
    public Product findById(String requestId) {
        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestId);

        // Pengecekan jika id nya tidak di temukan maka akan throw
        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        // mengirimkan hasil findById
        return optionalProduct.get();
    }
    @Override
    public String deleteById(String requestId) {
        // Mencari apakah ada id nya
        Optional<Product> optionalProduct = productRepository.findById(requestId);

        // Pengecekan jika id nya tidak ditemukan maka akan throw
        if (optionalProduct.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

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
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        // Mengupdate berdasarkan id, jika tidak terkena throw
        Product updateProduct = productRepository.save(requestProduct);

        return updateProduct;
    }

    private static Specification<Product> getProductSpecification(SearchProductRequest request) {
        Specification<Product> specification= (root, query, criteriaBuilder) -> {

            // Menampung predicates
            List<Predicate> predicates= new ArrayList<>();

            // jika request.getName nya tidak null maka akan di buatkan predicates
            if (request.getName() != null){
                // name like :name
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%");
                predicates.add(namePredicate);
            }
            // jika request.GetMinPrice nya tidak null maka akan di buatkan predicates
            if (request.getMinPrice() != null){
                // price >= :minPrice
                Predicate minPricePreadicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice());
                predicates.add(minPricePreadicate);
            }
            // jika request.GetMaxPrice nya tidak null maka akan di buatkan predicates
            if (request.getMaxPirce() !=null){
                // price <= :maxPrice
                Predicate maxPricePredicares = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPirce());
                predicates.add(maxPricePredicares);
            }
            // kembalikan nilai predicate
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return specification;
    }


}
