package com.example.productmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {


    @NotNull(message = "Product name should not be null")
    private String name;
    @NotNull(message = "Product description should not be null")
    private String description;
    @NotNull(message = "Product price should not be null")
    private float price;


}
