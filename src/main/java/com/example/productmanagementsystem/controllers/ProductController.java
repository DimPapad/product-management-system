package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;
import com.example.productmanagementsystem.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService=productService;
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
       return productService.getAllProducts();
    }

    @GetMapping("/name")
    public ProductDto getProductByName(@RequestBody ProductDto productDto) {
        return productService.getProductByName(productDto);
    }

    @PostMapping("/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping("/edit")
    public ProductDto editProduct(@RequestBody ChangedProductDto changedProductDto) {
        return productService.editProduct(changedProductDto);
    }

    @DeleteMapping("/delete/{productUuid}")
    public ProductDto deleteProduct(@PathVariable String productUuid) {
        return productService.deleteProduct(productUuid);
    }


}
