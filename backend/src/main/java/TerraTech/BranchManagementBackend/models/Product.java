package TerraTech.BranchManagementBackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false)
    Integer price;
    @Column(nullable = false)
    String producer;
    @Column(nullable = false)
    Integer inStock;
    @Column(nullable = false)
    LocalDate addedAt;
    @JsonIgnore
    @ManyToOne
    User manager;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Report> reports;
}
