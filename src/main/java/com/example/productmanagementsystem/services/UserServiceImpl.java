package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.models.Role;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.RoleRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    @Override
    @Transactional
    public UserDto registerUser(NewUserDto newUserDto) {
        if (newUserDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password is needed.");
        }
        if (newUserDto.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is needed.");
        }
        if (newUserDto.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username is needed.");
        }
        if (!newUserDto.getPassword().equals(newUserDto.getMatchingPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Passwords do not match.");
        }
        if (userRepository.findByEmail(newUserDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Email already exists.");
        }
        if (userRepository.findByUsername(newUserDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,"Username already exists.");
        }
        User newUser = new User();
        newUser.setLastName(newUserDto.getLastName());
        newUser.setFirstName(newUserDto.getFirstName());
        newUser.setUsername(newUserDto.getUsername());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUserDto.getPassword()));
        if  (roleRepository.findByName("ROLE_USER").isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is not ROLE_USER in the database, in order to be assigned to the new User.");
        }
        newUser.setRole(roleRepository.findByName("ROLE_USER").get());
        userRepository.save(newUser);
        UserDto userDto=new UserDto();
        userDto.setLastName(newUser.getLastName());
        userDto.setFirstName(newUser.getFirstName());
        userDto.setEmail(newUser.getEmail());
        userDto.setUsername(newUser.getUsername());
        userDto.setRole(newUser.getRole().getName());
        return userDto;
    }

    @Override
    public UserDto loggedInUser() {
        if (userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"User not found!");
        }
        User currentUser=userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        UserDto loggedInUserDto=new UserDto();
        loggedInUserDto.setLastName(currentUser.getLastName());
        loggedInUserDto.setFirstName(currentUser.getFirstName());
        loggedInUserDto.setUsername(currentUser.getUsername());
        loggedInUserDto.setEmail(currentUser.getEmail());
        loggedInUserDto.setRole(currentUser.getRole().getName());
        return loggedInUserDto;
    }

    @Override
    @Transactional
    public UserDto changeRole(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found!");
        }
        if (roleRepository.findByName(userDto.getRole()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found!");
        }
        User changedUser=userRepository.findByUsername(userDto.getUsername()).get();
        Role newRole=roleRepository.findByName(userDto.getRole()).get();
        changedUser.setRole(newRole);
        userRepository.save(changedUser);
        userDto.setLastName(changedUser.getLastName());
        userDto.setFirstName(changedUser.getFirstName());
        userDto.setEmail(changedUser.getEmail());
        return userDto;
    }


}
