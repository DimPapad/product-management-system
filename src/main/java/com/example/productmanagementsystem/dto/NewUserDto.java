package com.example.productmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class NewUserDto {


    @NotNull(message = "Last name should not be null")
    private String lastName;
    @NotNull(message = "First name should not be null")
    private String firstName;
    @NotNull(message = "Username should not be null")
    private String username;
    @NotNull(message = "Email should not be null")
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String email;
    @NotNull(message = "Password should not be null")
    private String password;
    @NotNull(message = "Please type your password again")
    private String matchingPassword;


}
