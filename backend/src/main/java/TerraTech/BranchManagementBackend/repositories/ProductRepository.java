package TerraTech.BranchManagementBackend.repositories;

import TerraTech.BranchManagementBackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import TerraTech.BranchManagementBackend.dto.chart.ChartData;
import TerraTech.BranchManagementBackend.dto.chart.BigChartRequest;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("""
                    SELECT MONTH(r.createDate) as month,
                           COALESCE(SUM(r.peopleNotified), 0) as notifiedCount, 
                           COALESCE(SUM(r.peopleSold), 0) as soldCount 
                    FROM Product p 
                    JOIN p.reports r 
                    WHERE YEAR(r.createDate) = :year AND p.id = :id 
                    GROUP BY MONTH(r.createDate)
            """)
    List<Object[]> productMonthlyReport(@Param("year") int year, @Param("id") long id);


    //------------------------------------------------------------------------------
    @Query("""
            SELECT SUM(p.price * r.peopleSold) * 100.0 / (SELECT SUM(p2.price * r2.peopleSold)
            FROM Product p2 JOIN p2.reports r2 
            WHERE YEAR(r2.createDate) = YEAR(CURRENT_DATE) AND p2.manager.id = :managerId) FROM Product p 
            JOIN p.reports r WHERE YEAR(r.createDate) = YEAR(CURRENT_DATE) AND p.manager.id = :managerId 
            AND MONTH(r.createDate) = MONTH(CURRENT_DATE)
            """)
    Double totalRevenueThisMonthPercentage(@Param("managerId") Long managerId);

    @Query("SELECT SUM(revenue) " + "FROM (SELECT SUM(p.price * r.peopleSold) as revenue " + "      FROM Product p " + "      LEFT JOIN p.reports r " + "      WHERE p.manager.id = :managerId AND YEAR(r.createDate) = YEAR(CURRENT_DATE) " + "      GROUP BY p.id) AS revenue_table")
    Long totalRevenueThisYear(@Param("managerId") Long managerId);

    @Query("SELECT NEW TerraTech.BranchManagementBackend.dto.chart.ChartData(product.name, SUM(product.price * report.peopleSold)) " + "FROM Product product " + "LEFT JOIN product.reports report " + "WHERE product.manager.id = :id AND YEAR(product.addedAt)=YEAR(CURRENT_DATE)" + "GROUP BY product.id " + "ORDER BY COUNT(report) DESC")
    List<ChartData> findTop5Products(@Param("id") Long id);

    @Query("SELECT new TerraTech.BranchManagementBackend.dto.chart.BigChartRequest(p.name, SUM(p.price * r.peopleSold) as totalProfit) " + "FROM Product p " + "JOIN p.reports r " + "WHERE MONTH(r.createDate) = :month " + "AND YEAR(r.createDate) = :year " + "AND p.manager.id = :id " + "GROUP BY p.id, p.name " + "ORDER BY totalProfit DESC")
    List<BigChartRequest> findTop3ProductsThisMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(product.price + report.peopleSold) " + "FROM Product product " + "JOIN product.reports report " + "WHERE MONTH(product.addedAt) = :month " + "AND YEAR(product.addedAt) = :year " + "AND product.manager.id = :id")
    Long productsSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(p.price * r.peopleSold) * 100.0 / (SELECT SUM(p2.price * r2.peopleSold) " + "FROM Product p2 JOIN p2.reports r2 WHERE YEAR(r2.createDate) = YEAR(CURRENT_DATE) AND p2.manager.id = :id) " + "FROM Product p JOIN p.reports r WHERE YEAR(r.createDate) = YEAR(CURRENT_DATE) AND p.manager.id = :id " + "AND MONTH(r.createDate) = MONTH(CURRENT_DATE)")
    Double newProductThisMonthPercentage(@Param("id") Long id);

    @Query("SELECT SUM(p.price * r.peopleSold) " + "FROM Product p " + "JOIN p.reports r " + "WHERE MONTH(p.addedAt) = :month " + "AND YEAR(p.addedAt) = :year " + "AND p.manager.id = :id")
    Long totalRevenueForMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.manager.id = :id")
    Long productsCount(@Param("id") Long id);

    @Query("SELECT SUM(r.peopleNotified) FROM Product p JOIN p.reports r WHERE MONTH(r.createDate) = :month AND YEAR(r.createDate) = :year AND p.id = :id")
    Long productNotifiedPeopleSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT SUM(r.peopleSold) FROM Product p JOIN p.reports r WHERE MONTH(r.createDate) = :month AND YEAR(r.createDate) = :year AND p.id = :id")
    Long productPeopleSoldToSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findByManagerId(Long id);
}
