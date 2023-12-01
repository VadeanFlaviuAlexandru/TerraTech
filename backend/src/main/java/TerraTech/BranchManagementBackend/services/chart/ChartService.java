package TerraTech.BranchManagementBackend.services.chart;

import TerraTech.BranchManagementBackend.dto.chart.ChartBoxRequest;
import TerraTech.BranchManagementBackend.dto.chart.ChartData;
import TerraTech.BranchManagementBackend.dto.chart.StatisticsResponse;
import TerraTech.BranchManagementBackend.dto.chart.TopDealUsersRequest;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ChartService {

    private final ReportRepository reportRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public StatisticsResponse fetchStatistics(Long id) {
        List<ChartData> listForTotalUsers = new ArrayList<>();
        List<ChartData> listForTotalProducts = new ArrayList<>();
        List<ChartData> listForTotalProductsMonthly = new ArrayList<>();
        List<ChartData> listForTotalNotifiedPeople = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long usersCount = userRepository.usersSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long productCount = productRepository.productsSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalRevenueForMonth = productRepository.totalRevenueForMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalNotifiedPeopleForMonth = reportRepository.notifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            ChartData dataUsers = ChartData.builder().value(usersCount).name(currentMonth.getMonth().name()).build();
            ChartData dataProducts = ChartData.builder().value(productCount).name(currentMonth.getMonth().name()).build();
            ChartData dataTotalProductsMonthly = ChartData.builder().value(totalRevenueForMonth).name(currentMonth.getMonth().name()).build();
            ChartData dataTotalPeopleNotified = ChartData.builder().value(totalNotifiedPeopleForMonth).name(currentMonth.getMonth().name()).build();
            listForTotalProductsMonthly.add(dataTotalProductsMonthly);
            listForTotalUsers.add(dataUsers);
            listForTotalProducts.add(dataProducts);
            listForTotalNotifiedPeople.add(dataTotalPeopleNotified);
        }

        List<TopDealUsersRequest> topUsers = (reportRepository.findTopProfitUsers(id)).subList(0, Math.min(reportRepository.findTopProfitUsers(id).size(), 5));
        ChartBoxRequest totalUsers = ChartBoxRequest.builder().dataKey("value").title("Total users").number(userRepository.usersCount(id)).percentage(userRepository.newUsersThisMonthPercentage(id)).chartData(listForTotalUsers).build();
        ChartBoxRequest totalProducts = ChartBoxRequest.builder().dataKey("value").title("Total products").number(productRepository.productsCount(id)).percentage(productRepository.newProductThisMonthPercentage(id)).chartData(listForTotalProducts).build();
        List<ChartData> top5ProductsThisYear = (productRepository.findTop5Products(id)).subList(0, Math.min(productRepository.findTop5Products(id).size(), 5));
        ChartBoxRequest totalRevenueThisYear = ChartBoxRequest.builder().dataKey("value").title("Total revenue this year").number(productRepository.totalRevenueThisYear(id)).percentage(productRepository.totalRevenueThisMonthPercentage(id)).chartData(listForTotalProductsMonthly).build();
        ChartBoxRequest totalNotifiedPeople = ChartBoxRequest.builder().dataKey("value").title("Total notified people").number(reportRepository.totalNotifiedPeople(id)).percentage(reportRepository.totalNotifiedPeoplePercentage(id)).chartData(listForTotalNotifiedPeople).build();

        return StatisticsResponse.builder().topDealUsers(topUsers).totalUsers(totalUsers).totalProducts(totalProducts).top5ProductsThisYear(top5ProductsThisYear).totalRevenueThisYear(totalRevenueThisYear).totalNotifiedPeople(totalNotifiedPeople).build(); //.top3ProductsThisMonth(top3ProductsThisMonth)
    }
}
