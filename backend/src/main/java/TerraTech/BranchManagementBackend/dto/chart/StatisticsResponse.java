package TerraTech.BranchManagementBackend.dto.chart;

import java.util.List;

public record StatisticsResponse(List<TopDealUsersRequest> topDealUsers, ChartBoxRequest totalUsers,
                                 ChartBoxRequest totalProducts, List<ChartData> top5ProductsThisYear,
                                 ChartBoxRequest totalRevenueThisYear, ChartBoxRequest totalNotifiedPeople) {
}