package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.exceptions.PasswordNotMatchingException;
import com.example.productmanagementsystem.models.User;

import java.util.List;

public interface UserService {


//    List<User> getAllUsers();
//
//    User getUserByEmail(String email);
//
//    List<User> getAllUsersByProductUuid(String productUuid);

    UserDto registerUser(NewUserDto newUserDto);


}
