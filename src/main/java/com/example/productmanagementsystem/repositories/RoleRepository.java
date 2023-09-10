package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {


    Optional<Role> findByName(String roleUser);


}
