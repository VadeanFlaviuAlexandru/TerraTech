package TerraTech.BranchManagementBackend.services.chart;

import TerraTech.BranchManagementBackend.dto.chart.ChartBoxRequest;
import TerraTech.BranchManagementBackend.dto.chart.ChartData;
import TerraTech.BranchManagementBackend.dto.chart.StatisticsResponse;
import TerraTech.BranchManagementBackend.dto.chart.TopDealUsersRequest;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChartService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private ChartBoxRequest createChartBoxRequest(String title, long number, double percentage, List<ChartData> chartData) {
        return ChartBoxRequest.builder()
                .dataKey("value")
                .title(title)
                .number(number)
                .percentage(percentage)
                .chartData(chartData)
                .build();
    }
    private List<ChartData> mapToChartData(List<Object[]> data) {
        return data.stream()
                .map(row -> ChartData.builder()
                        .value(((Number) row[1]).longValue())
                        .name(Month.of(((Number) row[0]).intValue()).name())
                        .build())
                .collect(Collectors.toList());
    }

    public StatisticsResponse fetchStatistics(long id) {
        var year = LocalDate.now().getYear();

        List<ChartData> listForTotalUsers = mapToChartData(userRepository.usersCountByMonth(year, id));
        List<ChartData> listForTotalProducts = mapToChartData(productRepository.productsSumByMonth(year, id));
        List<ChartData> listForTotalProductsMonthly = mapToChartData(productRepository.totalRevenueForMonth(year, id));
        List<ChartData> listForTotalNotifiedPeople = mapToChartData(reportRepository.notifiedPeopleSumByMonth(year, id));

        List<TopDealUsersRequest> topUsers = reportRepository.findTopProfitUsers(id).subList(0, Math.min(reportRepository.findTopProfitUsers(id).size(), 5));

        double newUsersThisMonthPercentage = userRepository.newUsersThisMonthPercentage(id);
        double newProductThisMonthPercentage = productRepository.newProductThisMonthPercentage(id);

        ChartBoxRequest totalUsers = createChartBoxRequest("Total users", userRepository.usersCount(id), newUsersThisMonthPercentage, listForTotalUsers);
        ChartBoxRequest totalProducts = createChartBoxRequest("Total products", productRepository.productsCount(id), newProductThisMonthPercentage, listForTotalProducts);

        List<ChartData> top5ProductsThisYear = productRepository.findTop5Products(id).subList(0, Math.min(productRepository.findTop5Products(id).size(), 5));

        ChartBoxRequest totalRevenueThisYear = createChartBoxRequest("Total revenue this year", productRepository.totalRevenueThisYear(id), productRepository.totalRevenueThisMonthPercentage(id), listForTotalProductsMonthly);
        ChartBoxRequest totalNotifiedPeople = createChartBoxRequest("Total notified people", reportRepository.totalNotifiedPeople(id), reportRepository.totalNotifiedPeoplePercentage(id), listForTotalNotifiedPeople);

        return new StatisticsResponse(topUsers, totalUsers, totalProducts, top5ProductsThisYear, totalRevenueThisYear, totalNotifiedPeople);
    }
}
