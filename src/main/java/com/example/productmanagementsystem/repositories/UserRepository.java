package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmail(String email);

    @Query(value = """
                    select u.*
                    from product_management_system.users u
                    inner join product_management_system.products_users pu
                    on pu.user_uuid=u.uuid
                    where pu.product_uuid=?1
                    """,
            nativeQuery = true)
    List<User> findAllByProductUuid(String productUuid);


}
