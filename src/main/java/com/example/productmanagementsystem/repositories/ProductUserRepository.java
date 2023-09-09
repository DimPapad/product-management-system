package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserRepository extends JpaRepository<ProductUser,String> {
}
