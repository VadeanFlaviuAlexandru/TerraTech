package TerraTech.BranchManagementBackend.repositories;

import TerraTech.BranchManagementBackend.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import TerraTech.BranchManagementBackend.dto.chart.TopDealUsersRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT MONTHS.MONTH as month, COALESCE(SUM(report.peopleNotified), 0) as notifiedCount, COALESCE(SUM(report.peopleSold), 0) as soldCount " + "FROM (SELECT 1 as MONTH UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) MONTHS " + "LEFT JOIN Report report ON MONTH(report.createDate) = MONTHS.MONTH AND YEAR(report.createDate) = :year AND report.user.id = :id " + "GROUP BY MONTHS.MONTH")
    List<Object[]> userMonthlyReport(@Param("year") int year, @Param("id") long id);


    //----------------------------------------------------------------------------------------------------
    @Query("SELECT NEW TerraTech.BranchManagementBackend.dto.chart.TopDealUsersRequest(user.firstName, user.email, SUM(report.peopleSold)) " + "FROM Report report INNER JOIN report.user user " + "WHERE user.manager.id = :id AND YEAR(user.createdAt) = YEAR(CURRENT_DATE) " + "GROUP BY user.id ORDER BY SUM(report.peopleSold) DESC ")
    List<TopDealUsersRequest> findTopProfitUsers(@Param("id") Long id);

    @Query("SELECT NEW TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest(report.id, report.product.id, report.description, report.product.name, report.createDate, report.peopleNotified, report.peopleSold) " + "FROM Report report INNER JOIN report.user user " + "WHERE user.id = :id AND YEAR(user.createdAt) = YEAR(CURRENT_DATE) " + "GROUP BY user.id, report.id, report.description, report.product.name, report.createDate, report.peopleNotified, report.peopleSold ORDER BY report.createDate DESC")
    List<ReportRequest> findReports(@Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotified) " + "FROM Report report " + "WHERE YEAR(report.createDate) = YEAR(CURRENT_DATE) " + "AND report.product.manager.id = :id")
    Long totalNotifiedPeople(@Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotified) * 100.0 / " + "(SELECT SUM(report2.peopleNotified) " + "FROM Report report2 WHERE YEAR(report2.createDate) = YEAR(CURRENT_DATE) AND report2.product.manager.id = :id) " + "FROM Report report WHERE YEAR(report.createDate) = YEAR(CURRENT_DATE) " + "AND report.product.manager.id = :id AND MONTH(report.createDate) = MONTH(CURRENT_DATE)")
    Double totalNotifiedPeoplePercentage(@Param("id") Long id);


    @Query("SELECT SUM(report.peopleNotified) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.product.manager.id = :id")
    Long notifiedPeopleSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleSold) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.product.manager.id = :id")
    Long peopleSoldToSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotified) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.user.id = :id")
    Long userNotifiedPeopleSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleSold) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.user.id = :id")
    Long userPeopleSoldToSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    List<Report> findByUserId(Long id);

    List<Report> findByProductId(Long id);

}
