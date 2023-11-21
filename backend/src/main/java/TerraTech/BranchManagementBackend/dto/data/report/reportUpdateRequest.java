package TerraTech.BranchManagementBackend.dto.data.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class reportUpdateRequest {
    Optional<String> productId;
    Optional<String> description;
    Optional<Integer> peopleNotifiedAboutProduct;
    Optional<Integer> peopleSoldTo;
}