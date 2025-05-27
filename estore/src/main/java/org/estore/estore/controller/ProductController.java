package org.estore.estore.controllers;

import org.estore.estore.dto.request.AddProductRequest;
import org.estore.estore.dto.response.AddProductResponse;
import org.estore.estore.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/ap/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AddProductResponse> addProduct(@ModelAttribute AddProductRequest product){
        return ResponseEntity.status(CREATED)
                .body(productService.add(product));
    }
}