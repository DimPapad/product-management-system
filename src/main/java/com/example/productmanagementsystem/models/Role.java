package com.example.productmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Table(name = "roles")
@Entity
public class Role {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")    @Column(name = "uuid")
    private String uuid;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users;


}
