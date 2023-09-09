package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;

public interface UserService {


//    List<User> getAllUsers();
//
//    User getUserByEmail(String email);
//
//    List<User> getAllUsersByProductUuid(String productUuid);

    UserDto registerUser(NewUserDto newUserDto);

    UserDto loggedInUser();


}
