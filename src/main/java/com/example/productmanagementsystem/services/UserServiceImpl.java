package com.example.productmanagementsystem.services;

import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.ProductUserRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final ProductUserRepository productUserRepository;


    public UserServiceImpl(UserRepository userRepository, ProductUserRepository productUserRepository) {
        this.userRepository=userRepository;
        this.productUserRepository=productUserRepository;
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
        return userRepository.findAllById(productUserRepository.findAllByProductUuid(productUuid));
    }


}
