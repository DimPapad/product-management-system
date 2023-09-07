package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.exceptions.PasswordNotMatchingException;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.RoleRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    @Override
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
        newUser.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(newUser);
        UserDto userDto=new UserDto();
        userDto.setLastName(newUser.getLastName());
        userDto.setFirstName(newUser.getFirstName());
        userDto.setEmail(newUser.getEmail());
        userDto.setUsername(newUser.getUsername());
        return userDto;
    }


//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

//    @Override
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

//    @Override
//    public List<User> getAllUsersByProductUuid(String productUuid) {
//        return userRepository.findAllByProductUuid(productUuid);
//    }


}
