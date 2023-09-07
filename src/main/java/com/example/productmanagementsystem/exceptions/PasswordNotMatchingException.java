package com.example.productmanagementsystem.exceptions;

public class PasswordNotMatchingException extends Throwable {
    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
