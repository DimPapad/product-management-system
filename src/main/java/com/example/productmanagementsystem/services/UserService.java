package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.models.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsers();

    User getUserByEmail(String email);

    List<User> getAllUsersByProductUuid(String productUuid);


}
