package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {


    Optional<Product> findByName(String name);

    @Query(value = """
            UPDATE product_management_system.products_users
            SET action_log='ADDED'
            FROM
            (SELECT product_uuid, user_uuid, action_log, action_time
            FROM product_management_system.products_users
            where user_uuid='07bd0463-2bb8-4edc-bf36-40e3abd4c9f6'
            ORDER BY action_time DESC
            LIMIT 1
            ) AS subquery
            WHERE product_management_system.user_uuid=subquery.user_uuid;
            """,nativeQuery = true)
    void setActionLog(String actionLog);


}
