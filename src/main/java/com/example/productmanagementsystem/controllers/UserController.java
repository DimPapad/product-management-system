package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController (UserService userService) {
        this.userService=userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody NewUserDto newUserDto) {
        return userService.registerUser(newUserDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/login")
    public UserDto logInUser() {
        return new UserDto();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/home")
    public UserDto loggedInUser() {
        return userService.loggedInUser();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/changerole")
    public UserDto changeRole(@RequestBody UserDto userDto) {
        return userService.changeRole(userDto);
    }


}
