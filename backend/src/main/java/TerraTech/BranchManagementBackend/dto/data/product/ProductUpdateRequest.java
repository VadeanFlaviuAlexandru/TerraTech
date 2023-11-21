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
    Optional<String> name;
    Optional<Integer> price;
    Optional<String> producer;
    Optional<Integer> inStock;
}