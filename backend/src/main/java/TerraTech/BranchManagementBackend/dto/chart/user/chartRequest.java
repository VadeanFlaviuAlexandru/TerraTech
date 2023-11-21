package TerraTech.BranchManagementBackend.dto.chart.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import TerraTech.BranchManagementBackend.dto.chart.user.dataKeyRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.dataRequest;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class chartRequest {
    List<dataRequest> data;
    List<dataKeyRequest> dataKeys;
}
