package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;

public interface UserService {


    UserDto registerUser(NewUserDto newUserDto);

    UserDto loggedInUser();

    UserDto changeRole(UserDto userDto);


}
