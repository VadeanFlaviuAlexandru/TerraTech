package TerraTech.BranchManagementBackend.dto.data.product;

import TerraTech.BranchManagementBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String producer;
    private LocalDate addedAt;
    private Integer inStock;
    private Integer price;
    private Integer numberOfReports;
    private User manager;
}
