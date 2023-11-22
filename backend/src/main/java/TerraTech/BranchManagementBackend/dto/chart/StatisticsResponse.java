package TerraTech.BranchManagementBackend.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponse {
    private List<TopDealUsersRequest> topDealUsers;
    private ChartBoxRequest totalUsers;
    private ChartBoxRequest totalProducts;
    private List<ChartData> top5ProductsThisYear;
    private ChartBoxRequest totalRevenueThisYear;
    private ChartBoxRequest totalNotifiedPeople;
    //    List<bigChartRequest> top3ProductsThisMonth;
}
