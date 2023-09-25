package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.services.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "View all products",description = "Returns all products after authentication and user/admin authorization.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Products successfully retrieved.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
       return productService.getAllProducts();
    }

    @Operation(summary = "View one product",description = "Returns one product after authentication and user/admin authorization, providing its name in the request body.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Product successfully retrieved."),
            @ApiResponse(code = 400,message = "There is no product with this name.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/name")
    public ProductDto getProductByName(@Parameter(name = "Product", description = "Product Name") @RequestBody ProductDto productDto) {
        return productService.getProductByName(productDto);
    }

    @Operation(summary = "Add a product",description = "Adds a product after authentication and user/admin authorization, providing all necessary fields in the request body. Action log and action time are recorded in database. Returns product information.")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "Product successfully created."),
            @ApiResponse(code = 400,message = "All fields are necessary."),
            @ApiResponse(code = 208,message = "Product already exists.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public ProductDto addProduct(@Parameter(name = "Product", description = "Product Information") @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @Operation(summary = "Edit a product",description = "Edits a product after authentication and admin authorization, providing its old name and the fields to be changed in the request body. Action log and action time are recorded in database. Returns edited product information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Product successfully edited."),
            @ApiResponse(code = 400,message = "Product not found.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/edit")
    public ProductDto editProduct(@Parameter(name = "Product", description = "Product Information") @RequestBody ChangedProductDto changedProductDto) {
        return productService.editProduct(changedProductDto);
    }

    @Operation(summary = "Delete a product",description = "Deletes a product after authentication and admin authorization, providing its UUID as variable in URI path. Action log and action time are recorded in database. Returns deleted product information.")
    @ApiResponses(value = {
            @ApiResponse(code = 204,message = "Product successfully deleted."),
            @ApiResponse(code = 400,message = "Product not found.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{productUuid}")
    public ProductDto deleteProduct(@Parameter(name = "UUID", description = "Product UUID", example = "09dcecf6-84f0-4241-82cd-da5a1ca285cc") @PathVariable String productUuid) {
        return productService.deleteProduct(productUuid);
    }


}
