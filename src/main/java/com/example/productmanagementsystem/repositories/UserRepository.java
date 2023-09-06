package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmail(String email);

    @Query(value = """
                    select u
                    from User u
                    inner join u.products p
                    where p.uuid=?1
                    """)
    List<User> findAllByProductUuid(String productUuid);


}
