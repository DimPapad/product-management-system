package com.example.productmanagementsystem.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Table(name = "products_users")
@Entity
public class ProductUser implements Serializable {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "product_user_uuid")
    private String uuid;
    @Column(name = "user_uuid")
    private String userUuid;
    @Column(name = "product_uuid")
    private String productUuid;
    @Column(name = "action_log")
    private String actionLog;
    @Column(name = "action_time")
    private Date actionTime;


}
