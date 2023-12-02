package TerraTech.BranchManagementBackend.repositories;

import TerraTech.BranchManagementBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT MONTHS.month as month, COALESCE(COUNT(u), 0) as userCount
            FROM (SELECT 1 as month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
            UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
            UNION SELECT 11 UNION SELECT 12) as MONTHS
            LEFT JOIN User u ON MONTH(u.createdAt) = MONTHS.month AND YEAR(u.createdAt) = :year AND u.manager.id = :id
            GROUP BY MONTHS.month
            ORDER BY MONTHS.month
            """)
    List<Object[]> usersCountByMonth(@Param("year") int year, @Param("id") long id);


    @Query("""
            SELECT CASE WHEN COUNT(u) = 0 THEN 0 ELSE COUNT(u) * 100.0 / (SELECT COUNT(u2) FROM User u2 
            WHERE YEAR(u2.createdAt) = YEAR(CURRENT_DATE) AND u2.manager.id = :id) END FROM User u 
            WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE) AND u.manager.id = :id AND MONTH(u.createdAt) = MONTH(CURRENT_DATE)
            """)
    Double newUsersThisMonthPercentage(@Param("id") long id);

    @Query("""
            SELECT COUNT(user) FROM User user WHERE user.manager.id = :id
            """)
    long usersCount(@Param("id") long id);

    @Query("""
            SELECT u from User u WHERE u.role = 'ROLE_MANAGER'
            """)
    List<User> getAllManagers();

    @Query("""
            SELECT user FROM User user WHERE user.manager.id = :managerId AND user.role = 'ROLE_EMPLOYEE'
            """)
    List<User> findEmployeesByManagerId(@Param("managerId") long managerId);

    Optional<User> findByEmail(String email);

    List<User> findByManagerId(long managerId);

}