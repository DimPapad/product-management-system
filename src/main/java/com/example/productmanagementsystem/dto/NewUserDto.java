package com.example.productmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewUserDto {


    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String password;
    private String matchingPassword;


}
