package com.example.productmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChangedProductDto implements Serializable {


    private String oldName;
    private String newName;
    private String description;
    private float price;


}
