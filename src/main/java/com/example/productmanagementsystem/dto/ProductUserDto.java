package com.example.productmanagementsystem.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductUserDto {


    private String userUuid;
    private String actionLog;
    private Date actionTime;


}
