package TerraTech.BranchManagementBackend.repositories;

import TerraTech.BranchManagementBackend.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import TerraTech.BranchManagementBackend.dto.chart.topDealUsersRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;

import java.math.BigInteger;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT NEW TerraTech.BranchManagementBackend.dto.chart.topDealUsersRequest(user.firstName, user.email, SUM(report.peopleSoldTo)) " + "FROM Report report INNER JOIN report.user user " + "WHERE user.manager.id = :id AND YEAR(user.createdAt) = YEAR(CURRENT_DATE) " + "GROUP BY user.id ORDER BY SUM(report.peopleSoldTo) DESC ")
    List<topDealUsersRequest> findTopProfitUsers(@Param("id") Long id);

    @Query("SELECT NEW TerraTech.BranchManagementBackend.dto.chart.user.reportRequest(report.id, report.description, report.product.name, report.createDate, report.peopleNotifiedAboutProduct, report.peopleSoldTo) " +
            "FROM Report report INNER JOIN report.user user " +
            "WHERE user.id = :id AND YEAR(user.createdAt) = YEAR(CURRENT_DATE) " +
            "GROUP BY user.id, report.id, report.description, report.product.name, report.createDate, report.peopleNotifiedAboutProduct, report.peopleSoldTo ORDER BY report.createDate DESC")
    List<reportRequest> findReports(@Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotifiedAboutProduct) " + "FROM Report report " + "WHERE YEAR(report.createDate) = YEAR(CURRENT_DATE) " + "AND report.product.manager.id = :id")
    Long totalNotifiedPeople(@Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotifiedAboutProduct) * 100.0 / " + "(SELECT SUM(report2.peopleNotifiedAboutProduct) " + "FROM Report report2 WHERE YEAR(report2.createDate) = YEAR(CURRENT_DATE) AND report2.product.manager.id = :id) " + "FROM Report report WHERE YEAR(report.createDate) = YEAR(CURRENT_DATE) " + "AND report.product.manager.id = :id AND MONTH(report.createDate) = MONTH(CURRENT_DATE)")
    Double totalNotifiedPeoplePercentage(@Param("id") Long id);


    @Query("SELECT SUM(report.peopleNotifiedAboutProduct) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.product.manager.id = :id")
    Long notifiedPeopleSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleSoldTo) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.product.manager.id = :id")
    Long peopleSoldToSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleNotifiedAboutProduct) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.user.id = :id")
    Long userNotifiedPeopleSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(report.peopleSoldTo) FROM Report report WHERE MONTH(report.createDate) = :month AND YEAR(report.createDate) = :year AND report.user.id = :id")
    Long userPeopleSoldToSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    List<Report> findByUserId(Long id);

    List<Report> findByProductId(Long id);

}
