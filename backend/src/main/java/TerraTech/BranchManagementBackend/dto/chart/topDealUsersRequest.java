package TerraTech.BranchManagementBackend.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class topDealUsersRequest {
    String firstName;
    String email;
    Long amount;
}
