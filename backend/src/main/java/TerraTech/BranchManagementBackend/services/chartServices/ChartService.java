package TerraTech.BranchManagementBackend.services.chartServices;

import TerraTech.BranchManagementBackend.dto.chart.chartBoxRequest;
import TerraTech.BranchManagementBackend.dto.chart.chartData;
import TerraTech.BranchManagementBackend.dto.chart.statisticsRequest;
import TerraTech.BranchManagementBackend.dto.chart.topDealUsersRequest;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.jwtServices.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public statisticsRequest fetchStatistics(Long id) {
        List<chartData> listForTotalUsers = new ArrayList<>();
        List<chartData> listForTotalProducts = new ArrayList<>();
        List<chartData> listForTotalProductsMonthly = new ArrayList<>();
        List<chartData> listForTotalNotifiedPeople = new ArrayList<>();
//        List<bigChartRequest> top3ProductsThisMonth = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate currentMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
            Long usersCount = userRepository.usersSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long productCount = productRepository.productsSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalRevenueForMonth = productRepository.totalRevenueForMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            Long totalNotifiedPeopleForMonth = reportRepository.notifiedPeopleSpecificMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
//            top3ProductsThisMonth = productRepository.findTop3ProductsThisMonth(currentMonth.getMonthValue(), currentMonth.getYear(), id);
            chartData dataUsers = chartData.builder().value(usersCount).name(currentMonth.getMonth().name()).build();
            chartData dataProducts = chartData.builder().value(productCount).name(currentMonth.getMonth().name()).build();
            chartData dataTotalProductsMonthly = chartData.builder().value(totalRevenueForMonth).name(currentMonth.getMonth().name()).build();
            chartData dataTotalPeopleNotified = chartData.builder().value(totalNotifiedPeopleForMonth).name(currentMonth.getMonth().name()).build();
            listForTotalProductsMonthly.add(dataTotalProductsMonthly);
            listForTotalUsers.add(dataUsers);
            listForTotalProducts.add(dataProducts);
            listForTotalNotifiedPeople.add(dataTotalPeopleNotified);
        }

        List<topDealUsersRequest> topUsers = (reportRepository.findTopProfitUsers(id)).subList(0, Math.min(reportRepository.findTopProfitUsers(id).size(), 5));
        chartBoxRequest totalUsers = chartBoxRequest.builder().dataKey("value").title("Total users").number(userRepository.usersCount(id)).percentage(userRepository.newUsersThisMonthPercentage(id)).chartData(listForTotalUsers).build();
        chartBoxRequest totalProducts = chartBoxRequest.builder().dataKey("value").title("Total products").number(productRepository.productsCount(id)).percentage(productRepository.newProductThisMonthPercentage(id)).chartData(listForTotalProducts).build();
        List<chartData> top5ProductsThisYear = (productRepository.findTop5Products(id)).subList(0, Math.min(productRepository.findTop5Products(id).size(), 5));
        chartBoxRequest totalRevenueThisYear = chartBoxRequest.builder().dataKey("value").title("Total revenue this year").number(productRepository.totalRevenueThisYear(id)).percentage(productRepository.totalRevenueThisMonthPercentage(id)).chartData(listForTotalProductsMonthly).build();
        chartBoxRequest totalNotifiedPeople = chartBoxRequest.builder().dataKey("value").title("Total notified people").number(reportRepository.totalNotifiedPeople(id)).percentage(reportRepository.totalNotifiedPeoplePercentage(id)).chartData(listForTotalNotifiedPeople).build();

        return statisticsRequest.builder().topDealUsers(topUsers).totalUsers(totalUsers).totalProducts(totalProducts).top5ProductsThisYear(top5ProductsThisYear).totalRevenueThisYear(totalRevenueThisYear).totalNotifiedPeople(totalNotifiedPeople).build(); //.top3ProductsThisMonth(top3ProductsThisMonth)
    }
}
