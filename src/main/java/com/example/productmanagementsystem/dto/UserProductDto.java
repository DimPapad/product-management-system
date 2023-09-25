package com.example.productmanagementsystem.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserProductDto {


    private String productUuid;
    private String actionLog;
    private Date actionTime;


}
