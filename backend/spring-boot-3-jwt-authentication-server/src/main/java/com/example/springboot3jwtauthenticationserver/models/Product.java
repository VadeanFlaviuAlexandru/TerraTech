package com.example.springboot3jwtauthenticationserver.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data //generates getters, setters, equals, hashCode for the fields in the class
@Builder //generates a builder class that simplifies the process of creating complex objects
@NoArgsConstructor //generates a no-argument constructor for the class
@AllArgsConstructor //generates a constructor that initializes all fields of the class
@Entity
//it indicates that instances of this class should be stored in a database. The @Entity annotation is typically used for defining database tables and their relationships.
@ToString //generates a toString method for the class
@Table(name = "products") //used to specify the name of the database table associated with the entity
public class Product implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer price;
    String producer;
    Integer inStock;
    LocalDate addedAt;
    @ElementCollection
    List<String> colours;
    @Column(unique = true)
    String name;
    @OneToMany(mappedBy = "product")
    private List<Activity> activities;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
