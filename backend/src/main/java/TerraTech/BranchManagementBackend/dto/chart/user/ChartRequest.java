package TerraTech.BranchManagementBackend.dto.chart.user;

import java.util.List;


public record ChartRequest(
        List<DataRequest> data,
        List<DataKeyRequest> dataKeys
) {
}
