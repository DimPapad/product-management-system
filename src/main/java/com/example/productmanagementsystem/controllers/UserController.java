package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.exceptions.PasswordNotMatchingException;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/all")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/email/{email}")
//    public User getUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email);
//    }
//
//    @GetMapping("/product_uuid/{productUuid}")
//    public List<User> getAllUsersByProductUuid(@PathVariable String productUuid) {
//        return  userService.getAllUsersByProductUuid(productUuid);
//    }


}
