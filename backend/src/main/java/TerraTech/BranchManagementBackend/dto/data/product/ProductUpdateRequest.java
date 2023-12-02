package TerraTech.BranchManagementBackend.dto.data.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String name;
    private int price;
    private String producer;
    private int inStock;
}