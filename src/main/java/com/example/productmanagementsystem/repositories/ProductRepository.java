package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {


    Optional<Product> findByName(String name);

    @Query(value = """
                        WITH subquery AS
                        (
                        SELECT product_uuid, user_uuid, action_log, action_time
                            FROM product_management_system.products_users
                            WHERE user_uuid=?1
                            ORDER BY action_time DESC
                            LIMIT 1
                        )
                        UPDATE ONLY product_management_system.products_users as mytable
                            SET action_log=?2
                            FROM subquery
                            WHERE mytable.action_time=subquery.action_time
                                AND mytable.user_uuid=subquery.user_uuid
                                AND mytable.product_uuid=subquery.product_uuid
                        RETURNING mytable.product_uuid;
                        """,nativeQuery = true)
    void setActionLog(String userUuid, String actionLog);


}
