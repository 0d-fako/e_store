package org.estore.estore.service;

import org.estore.estore.dto.request.AddProductRequest;
import org.estore.estore.dto.response.AddProductResponse;
import org.estore.estore.model.Product;

public interface ProductService {
    Product getProductBy(String id);

    AddProductResponse add(AddProductRequest productRequest);
}
