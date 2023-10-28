package com.example.springboot3jwtauthenticationserver.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data //generates getters, setters, equals, hashCode for the fields in the class
@SuperBuilder //generates a builder class that simplifies the process of creating complex objects
@NoArgsConstructor //generates a no-argument constructor for the class
@AllArgsConstructor //generates a constructor that initializes all fields of the class
@Entity
//it indicates that instances of this class should be stored in a database. The @Entity annotation is typically used for defining database tables and their relationships.
@ToString //generates a toString method for the class
@Table(name = "users") //used to specify the name of the database table associated with the entity
public class User implements UserDetails { //UserDetails is an interface that includes various methods.

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstName;
    String lastName;
    String password;
    LocalDate createdAt;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String phone;
    @Enumerated(EnumType.STRING)
    Role role;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Activity> activities;

    public User(String firstName, String lastName, String password, LocalDate createdAt, String email, String phone, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.createdAt = createdAt;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Integer numberOfActivities() {
        return activities.size();
    }

    public Manager returnManager() {
        return manager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
