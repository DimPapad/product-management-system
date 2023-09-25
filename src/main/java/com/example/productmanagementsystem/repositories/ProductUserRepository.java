package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.ProductUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserRepository extends JpaRepository<ProductUser,String> {


    List<ProductUser> findAllByUserUuid(String userUuid);

    List<ProductUser> findAllByProductUuid(String userUuid);


}
