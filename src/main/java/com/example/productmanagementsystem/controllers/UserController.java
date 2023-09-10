package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController (UserService userService) {
        this.userService=userService;
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody NewUserDto newUserDto) {
        return userService.registerUser(newUserDto);
    }

    @GetMapping("/login")
    public UserDto logInUser() {
        return null;
    }

    @GetMapping("/home")
    public UserDto loggedInUser() {
        return userService.loggedInUser();
    }

    @PutMapping("/changerole")
    public UserDto changeRole(@RequestBody UserDto userDto) {
        return userService.changeRole(userDto);
    }


}
