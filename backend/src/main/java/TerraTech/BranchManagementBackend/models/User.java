package TerraTech.BranchManagementBackend.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import TerraTech.BranchManagementBackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    LocalDate createdAt;
    @Column(unique = true)
    String email;
    @Column(nullable = false)
    String phone;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(nullable = false)
    boolean status;
    @JsonIgnore
    @ManyToOne
    User manager;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Report> report;
    @JsonIgnore
    @OneToMany(mappedBy = "manager")
    List<User> employees;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public void toggleStatus() {
        this.status = !this.status;
    }
}