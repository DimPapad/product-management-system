package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;
import com.example.productmanagementsystem.models.ProductUser;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.ProductRepository;
import com.example.productmanagementsystem.repositories.ProductUserRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductUserRepository productUserRepository;

    public ProductServiceImpl (ProductRepository productRepository, UserRepository userRepository, ProductUserRepository productUserRepository) {
        this.productRepository=productRepository;
        this.userRepository=userRepository;
        this.productUserRepository=productUserRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos=new ArrayList<>();
        productRepository.findAll()
                .forEach(product -> productDtos.add(
                        new ProductDto(product.getName(),product.getDescription(),product.getPrice())));
        if (productDtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No products found");
        }
        return productDtos;
    }

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        if (productDto.getName()==null || productDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is needed.");
        }
        if (productDto.getDescription()==null || productDto.getDescription().isBlank()) {
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
        productRepository.save(newProduct);

        User currentUser=userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        ProductUser newProductUser=new ProductUser();
        newProductUser.setProductUuid(newProduct.getUuid());
        newProductUser.setUserUuid(currentUser.getUuid());
        newProductUser.setActionLog("ADD");
        newProductUser.setActionTime(new Timestamp(System.currentTimeMillis()));
        productUserRepository.save(newProductUser);

        return productDto;
    }

    @Override
    @Transactional
    public ProductDto editProduct(ChangedProductDto changedProductDto) {
        if (productRepository.findByName(changedProductDto.getOldName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product not found!");
        }
        Product changedProduct=productRepository.findByName(changedProductDto.getOldName()).get();
        if (changedProductDto.getNewName()!=null && !changedProductDto.getNewName().isEmpty()) {
            changedProduct.setName(changedProductDto.getNewName());
        }
        if (changedProductDto.getDescription()!=null && !changedProductDto.getDescription().isEmpty()) {
            changedProduct.setDescription(changedProductDto.getDescription());
        }
        if (changedProductDto.getPrice()>0) {
            changedProduct.setPrice(changedProductDto.getPrice());
        }
        productRepository.save(changedProduct);

        User currentUser=userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        ProductUser newProductUser=new ProductUser();
        newProductUser.setUserUuid(currentUser.getUuid());
        newProductUser.setProductUuid(changedProduct.getUuid());
        newProductUser.setActionLog("EDIT");
        newProductUser.setActionTime(new Timestamp(System.currentTimeMillis()));
        productUserRepository.save(newProductUser);

        return new ProductDto(changedProduct.getName(), changedProduct.getDescription(), changedProduct.getPrice());
    }

    @Override
    @Transactional
    public ProductDto deleteProduct(String productUuid) {
        if (productRepository.findById(productUuid).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product not found!");
        }
        Product deletedProduct=productRepository.findById(productUuid).get();
        User currentUser=userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        ProductUser newProductUser=new ProductUser();
        newProductUser.setProductUuid(deletedProduct.getUuid());
        newProductUser.setUserUuid(currentUser.getUuid());
        newProductUser.setActionLog("DELETE");
        newProductUser.setActionTime(new Timestamp(System.currentTimeMillis()));
        productUserRepository.save(newProductUser);

        productRepository.deleteById(productUuid);

        return new ProductDto(deletedProduct.getName(), deletedProduct.getDescription(), deletedProduct.getPrice());
    }

    @Override
    public ProductDto getProductByName(ProductDto productDto) {
        if (productRepository.findByName(productDto.getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Product not found!");
        }
        Product product=productRepository.findByName(productDto.getName()).get();
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }


}
