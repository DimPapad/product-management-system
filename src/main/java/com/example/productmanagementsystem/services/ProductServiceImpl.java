package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;
import com.example.productmanagementsystem.repositories.ProductRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import com.example.productmanagementsystem.security.models.MyUserDetails;
import com.example.productmanagementsystem.security.services.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductServiceImpl (ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository=productRepository;
        this.userRepository=userRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        if (productDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is needed.");
        }
        if (productDto.getDescription().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product description is needed.");
        }
        if ((productDto.getPrice()<=0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product price is needed and should be valid.");
        }
        if (productRepository.findByName(productDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Product already exists.");
        }
        Product newProduct=new Product();
        newProduct.setName(productDto.getName());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());
        productRepository.setActionLog("ADD");
        return productDto;
    }


}
