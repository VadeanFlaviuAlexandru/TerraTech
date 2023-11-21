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
    List<TopDealUsersRequest> topDealUsers;
    ChartBoxRequest totalUsers;
    ChartBoxRequest totalProducts;
    List<ChartData> top5ProductsThisYear;
    ChartBoxRequest totalRevenueThisYear;
    ChartBoxRequest totalNotifiedPeople;
    //    List<bigChartRequest> top3ProductsThisMonth;
}
