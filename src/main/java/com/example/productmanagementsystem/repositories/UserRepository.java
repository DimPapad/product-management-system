package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query(value = """
                    select u
                    from User u
                    inner join u.products p
                    where p.uuid=?1
                    """)
    List<User> findAllByProductUuid(String productUuid);


}
