package com.example.productmanagementsystem.controllers;

import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController (UserService userService) {
        this.userService=userService;
    }

    @Operation(summary = "Get all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the users.",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Users not found.",
                    content = @Content) })
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found.",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid email supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Users not found.",
                    content = @Content) })
    @GetMapping("/email/{email}")
    public User getUserByEmail(@Parameter(description = "email of user to be searched") @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/product_uuid/{productUuid}")
    public List<User> getAllUsersByProductUuid(@PathVariable String productUuid) {
        return  userService.getAllUsersByProductUuid(productUuid);
    }


}
