package com.example.productmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductDto implements Serializable {


    private String name;
    private String description;
    private float price;


}
