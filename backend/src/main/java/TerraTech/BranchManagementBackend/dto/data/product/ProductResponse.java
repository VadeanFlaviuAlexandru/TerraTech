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
    Long id;
    String name;
    Integer price;
    String producer;
    Integer inStock;
    LocalDate addedAt;
    User manager;
}
