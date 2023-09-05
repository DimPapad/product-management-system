package com.example.productmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductUserRepository extends JpaRepository<String,String> {


    @Query(value = "select user_uuid from products_users where product_uuid=?1", nativeQuery = true)
    List<String> findAllByProductUuid(String productUuid);


}
