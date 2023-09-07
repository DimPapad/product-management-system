package com.example.productmanagementsystem.repositories;

import com.example.productmanagementsystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {


    Role findByName(String roleUser);


}
