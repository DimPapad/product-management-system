package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {


    Optional<Product> findByName(String name);


}
