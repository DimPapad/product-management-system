package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController (UserService userService) {
        this.userService=userService;
    }

    @Operation(summary = "User Registration",description = "User registration providing all necessary fields in the request body. Returns registered user information.")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "Successfully registered."),
            @ApiResponse(code = 400,message = "All fields are necessary. Passwords should match."),
            @ApiResponse(code = 208,message = "User already registered")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserDto registerUser(@Parameter(name = "User", description = "User Information") @RequestBody NewUserDto newUserDto) {
        return userService.registerUser(newUserDto);
    }

    @Operation(summary = "User Login Page",description = "User login page accessed without authorization.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Login page successfully accessed.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/login")
    public UserDto logInUser() {
        return null;
    }

    @Operation(summary = "User Home Page",description = "User home page accessed with authorization. Returns logged in user information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Home page successfully accessed and user information retrieved."),
            @ApiResponse(code = 204,message = "User not found.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/home")
    public UserDto loggedInUser() {
        return userService.loggedInUser();
    }

    @Operation(summary = "User Role Change",description = "User role change after authentication and admin authorization providing username and new role in the request body. Returns edited user information.")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "User role successfully changed and user information retrieved."),
            @ApiResponse(code = 400,message = "User or Role not found.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/changerole")
    public UserDto changeRole(@Parameter(name = "User", description = "User Information") @RequestBody UserDto userDto) {
        return userService.changeRole(userDto);
    }


}
