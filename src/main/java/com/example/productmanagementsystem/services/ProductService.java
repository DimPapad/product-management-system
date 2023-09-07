package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;

import java.util.List;

public interface ProductService {


    List<Product> getAllProducts();

    ProductDto addProduct(ProductDto productDto);


}
