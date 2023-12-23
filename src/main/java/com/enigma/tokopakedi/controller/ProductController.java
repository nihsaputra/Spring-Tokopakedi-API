package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<WebResponse<Product>> createNewProduct(@RequestBody Product requestProduct){

        Product newProduct = productService.createNew(requestProduct);

        WebResponse<Product> response= WebResponse.<Product>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("create new product successfully")
                .data(newProduct)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/products/bulk")
    public ResponseEntity<WebResponse<List<Product>>> createNewProductBulk(@RequestBody List<Product> products){

        List<Product> newProductBulk = productService.createNewBulk(products);

        WebResponse<List<Product>> response= WebResponse.<List<Product>>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("create new products successfuly")
                .data(newProductBulk)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/products/{requestId}")
    public ResponseEntity<WebResponse<Product>> findProductId(@PathVariable String requestId){

        Product findProductById = productService.findById(requestId);

        WebResponse<Product> response= WebResponse.<Product>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("succesfuly get product by id")
                .data(findProductById)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<WebResponse<List<Product>>> findProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false)Long maxPrice){

        SearchProductRequest request= SearchProductRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .minPrice(minPrice)
                .maxPirce(maxPrice)
                .build();

        Page<Product> products = productService.findAll(request);

        PagingResponse pagingResponse= PagingResponse.<Product>builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .build();

        WebResponse<List<Product>> response= WebResponse.<List<Product>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get all product")
                .paging(pagingResponse)
                .data(products.getContent())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "products")
    public ResponseEntity<WebResponse<Product>> updateProduct(@RequestBody Product requestProduct){

        Product updateProduct = productService.updateById(requestProduct);

        WebResponse<Product> response= WebResponse.<Product>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("update successfuly")
                .data(updateProduct)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/products/{requestId}")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable String requestId){

        String json = productService.deleteById(requestId);

        WebResponse<String> response= WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("delete successfuly")
                .build();

        return ResponseEntity.ok(response);
    }
}
