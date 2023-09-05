package com.example.productmanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@ToString
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


}
