package TerraTech.BranchManagementBackend.dto.chart;

import TerraTech.BranchManagementBackend.dto.data.user.managerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class statisticsRequest { //da verifica sa fie in acelasi an si id
    List<topDealUsersRequest> topDealUsers;
    chartBoxRequest totalUsers;
    chartBoxRequest totalProducts;
    List<chartData> top5ProductsThisYear;
    chartBoxRequest totalRevenueThisYear;
    chartBoxRequest totalNotifiedPeople;
    //    List<bigChartRequest> top3ProductsThisMonth;
}
