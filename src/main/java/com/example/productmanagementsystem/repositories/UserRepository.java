package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmail(String email);


}
