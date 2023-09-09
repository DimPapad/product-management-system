package com.example.productmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "products")
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private float price;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ProductUser> users=new HashSet<>();

//    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "products_users",
//            joinColumns = @JoinColumn(name = "product_uuid"),
//            inverseJoinColumns = @JoinColumn(name = "user_uuid"))
//    private Set<User> users=new HashSet<>();

    public void setUser(ProductUser productUser) {
        users.add(productUser);
    }

}
