package com.example.productmanagementsystem.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {


    private String lastName;
    private String firstName;
    @NotNull
    private String username;
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String email;
    @NotNull
    private String role;

    public UserDto(String username, String role) {
        this.username=username;
        this.role=role;
    }


}
