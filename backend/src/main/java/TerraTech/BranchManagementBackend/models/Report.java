package TerraTech.BranchManagementBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue
    long id;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDate createDate;
    @Column(nullable = false)
    int peopleNotified;
    @Column(nullable = false)
    int peopleSold;
    @JsonIgnore
    @ManyToOne
    User user;
    @JsonIgnore
    @ManyToOne
    Product product;
}
