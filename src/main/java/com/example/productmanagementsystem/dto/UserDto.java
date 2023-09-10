package com.example.productmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {


    private String lastName;
    private String firstName;
    @NotNull
    private String username;
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String email;
    @NotNull
    private String role;


}
