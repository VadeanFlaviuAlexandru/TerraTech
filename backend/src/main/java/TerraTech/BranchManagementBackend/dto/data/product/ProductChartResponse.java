package TerraTech.BranchManagementBackend.dto.data.product;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.models.Product;

public record ProductChartResponse(
        Product product,
        ChartRequest chartInfo
) {
}
