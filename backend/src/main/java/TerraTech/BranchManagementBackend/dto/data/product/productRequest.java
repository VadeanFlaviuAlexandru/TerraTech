package TerraTech.BranchManagementBackend.dto.data.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class productRequest {
    String name;
    Integer price;
    String producer;
    Integer inStock;
}
