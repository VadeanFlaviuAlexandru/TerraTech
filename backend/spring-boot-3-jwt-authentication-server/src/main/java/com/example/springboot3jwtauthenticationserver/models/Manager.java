package com.example.springboot3jwtauthenticationserver.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;


@SuperBuilder //generates a builder class that simplifies the process of creating complex objects
@NoArgsConstructor //generates a no-argument constructor for the class
@AllArgsConstructor //generates a constructor that initializes all fields of the class
@Entity
//it indicates that instances of this class should be stored in a database. The @Entity annotation is typically used for defining database tables and their relationships.
@ToString //generates a toString method for the class
@Table(name = "managers") //used to specify the name of the database table associated with the entity
public class Manager extends User {

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<User> employees;
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Product> products;

    public Manager(String firstName, String lastName, String password, LocalDate createdAt, String email, String phone, Role role, List<User> employees, List<Product> products) {
        super(firstName, lastName, password, createdAt, email, phone, role);
        this.employees = employees;
        this.products = products;
    }

    public Integer numberOfEmployees() {
        return employees.size();
    }
}

