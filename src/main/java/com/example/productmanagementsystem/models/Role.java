package com.example.productmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Table(name = "roles")
@Entity
@NoArgsConstructor
public class Role {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name")
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role(String name) {
        this.name=name;
    }

}
