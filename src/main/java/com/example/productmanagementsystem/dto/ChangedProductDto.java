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
public class ChangedProductDto implements Serializable {


    @NotNull(message = "Please give the name for the product to be changed")
    private String oldName;
    private String newName;
    private String description;
    private float price;


}
