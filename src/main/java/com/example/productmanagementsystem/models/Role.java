package com.example.productmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Entity
public class Role {


    @Id
    @UuidGenerator
    @Column(name = "uuid")
    private String uuid;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users;


}
