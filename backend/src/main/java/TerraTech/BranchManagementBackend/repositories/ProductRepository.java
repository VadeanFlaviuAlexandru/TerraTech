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

    @Query("""
            SELECT COALESCE(
                SUM(p.price * r.peopleSold) * 100.0 / NULLIF(
                    (SELECT SUM(p2.price * r2.peopleSold)
                     FROM Product p2 JOIN p2.reports r2
                     WHERE YEAR(r2.createDate) = YEAR(CURRENT_DATE) AND p2.manager.id = :managerId),0.0),0.0)
            FROM Product p JOIN p.reports r
            WHERE YEAR(r.createDate) = YEAR(CURRENT_DATE) AND p.manager.id = :managerId
                AND MONTH(r.createDate) = MONTH(CURRENT_DATE)
            """)
    double totalRevenueThisMonthPercentage(@Param("managerId") long managerId);

    @Query("""
            SELECT COALESCE(SUM(revenue), 0) FROM (SELECT SUM(p.price * r.peopleSold) as revenue
            FROM Product p LEFT JOIN p.reports r
            WHERE p.manager.id = :managerId AND YEAR(r.createDate) = YEAR(CURRENT_DATE)
            GROUP BY p.id) AS revenue_table
             """)
    long totalRevenueThisYear(@Param("managerId") long managerId);

    @Query("""
            SELECT NEW TerraTech.BranchManagementBackend.dto.chart.ChartData(product.name,
            SUM(product.price * report.peopleSold)) FROM Product product LEFT JOIN product.reports report
            WHERE product.manager.id = :id AND YEAR(product.addedAt)=YEAR(CURRENT_DATE) GROUP BY product.id
            ORDER BY COUNT(report) DESC
            """)
    List<ChartData> findTop5Products(@Param("id") long id);

    @Query("""
            SELECT MONTHS.month as month, COALESCE(SUM(product.price + report.peopleSold), 0) as totalValue
            FROM (SELECT 1 as month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
            UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
            UNION SELECT 11 UNION SELECT 12) as MONTHS
            LEFT JOIN Product product
            ON MONTH(product.addedAt) = MONTHS.month AND YEAR(product.addedAt) = :year AND product.manager.id = :id
            LEFT JOIN product.reports report
            GROUP BY MONTHS.month
            ORDER BY MONTHS.month
            """)
    List<Object[]> productsSumByMonth(@Param("year") int year, @Param("id") long id);


    @Query("""
            SELECT COALESCE( SUM(p.price * r.peopleSold) * 100.0 / NULLIF((SELECT SUM(p2.price * r2.peopleSold)
            FROM Product p2 JOIN p2.reports r2 WHERE YEAR(r2.createDate) = YEAR(CURRENT_DATE)
            AND p2.manager.id = :id), 0), 0.0) FROM Product p JOIN p.reports r
            WHERE YEAR(r.createDate) = YEAR(CURRENT_DATE) AND p.manager.id = :id
            AND MONTH(r.createDate) = MONTH(CURRENT_DATE)
            """)
    Double newProductThisMonthPercentage(@Param("id") long id);

    @Query("""
            SELECT MONTHS.month as month, COALESCE(SUM(product.price * report.peopleSold), 0) as totalRevenue
            FROM (SELECT 1 as month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
            UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
            UNION SELECT 11 UNION SELECT 12) as MONTHS
            LEFT JOIN Product product
            ON MONTH(product.addedAt) = MONTHS.month AND YEAR(product.addedAt) = :year AND product.manager.id = :id
            LEFT JOIN product.reports report
            GROUP BY MONTHS.month
            ORDER BY MONTHS.month
            """)
    List<Object[]> totalRevenueForMonth(@Param("year") int year, @Param("id") long id);


    @Query("""
            SELECT COUNT(p) FROM Product p WHERE p.manager.id = :id
            """)
    long productsCount(@Param("id") long id);

    Optional<Product> findById(long id);

    Optional<Product> findByName(String name);

    List<Product> findByManagerId(long id);
}
