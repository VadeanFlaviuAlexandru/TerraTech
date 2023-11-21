package TerraTech.BranchManagementBackend.repositories;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import TerraTech.BranchManagementBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) FROM User u WHERE MONTH(u.createdAt) = :month AND YEAR(u.createdAt) = :year AND u.manager.id = :id")
    Long usersSpecificMonth(@Param("month") Integer month, @Param("year") Integer year, @Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(u) = 0 THEN 0 ELSE COUNT(u) * 100.0 / " + "(SELECT COUNT(u2) FROM User u2 WHERE YEAR(u2.createdAt) = YEAR(CURRENT_DATE) AND u2.manager.id = :id) END " + "FROM User u WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE) AND u.manager.id = :id AND MONTH(u.createdAt) = MONTH(CURRENT_DATE)")
    Double newUsersThisMonthPercentage(@Param("id") Long id);

    @Query("SELECT COUNT(user) FROM User user WHERE user.manager.id = :id")
    Long usersCount(@Param("id") Long id);

    Optional<User> findByEmail(String email);

    List<User> findByManagerId(Long managerId);

    @Query("SELECT u from User u WHERE u.role = 'ROLE_MANAGER'")
    List<User> getAllManagers();

    @Query("SELECT user FROM User user WHERE user.manager.id = :managerId AND user.role = 'ROLE_EMPLOYEE'")
    List<User> findEmployeesByManagerId(@Param("managerId") Long managerId);
}