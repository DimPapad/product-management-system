package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService=productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
       return productService.getAllProducts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/name")
    public ProductDto getProductByName(@RequestBody ProductDto productDto) {
        return productService.getProductByName(productDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/edit")
    public ProductDto editProduct(@RequestBody ChangedProductDto changedProductDto) {
        return productService.editProduct(changedProductDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{productUuid}")
    public ProductDto deleteProduct(@PathVariable String productUuid) {
        return productService.deleteProduct(productUuid);
    }


}
