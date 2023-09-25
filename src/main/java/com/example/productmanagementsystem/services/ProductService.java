package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.dto.ProductUserDto;

import java.util.List;

public interface ProductService {


    List<ProductDto> getAllProducts();

    ProductDto addProduct(ProductDto productDto);

    ProductDto editProduct(ChangedProductDto changedProductDto);

    ProductDto deleteProduct(String productUuid);

    ProductDto getProductByName(ProductDto productDto);

    List<ProductUserDto> getProductAudit(String productUuid);


}
