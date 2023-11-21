package TerraTech.BranchManagementBackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    Long id;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDate createDate;
    @Column(nullable = false)
    Integer peopleNotifiedAboutProduct;
    @Column(nullable = false)
    Integer peopleSoldTo;
    @JsonIgnore
    @ManyToOne
    User user;
    @JsonIgnore
    @ManyToOne
    Product product;
}
