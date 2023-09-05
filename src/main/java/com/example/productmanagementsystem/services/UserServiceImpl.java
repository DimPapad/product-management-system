package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsersByProductUuid(String productUuid) {
        return userRepository.findAllByProductUuid(productUuid);
    }


}
