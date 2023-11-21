package TerraTech.BranchManagementBackend.dto.data.product;

import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;
import TerraTech.BranchManagementBackend.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class productResponse {
    Product product;
    chartRequest chartInfo;
}
