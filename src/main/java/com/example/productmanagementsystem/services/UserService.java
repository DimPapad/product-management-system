package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.dto.UserProductDto;

import java.util.List;

public interface UserService {


    UserDto registerUser(NewUserDto newUserDto);

    UserDto loggedInUser();

    UserDto changeRole(UserDto userDto);

    List<UserProductDto> getUserAudit(String userUuid);


}
