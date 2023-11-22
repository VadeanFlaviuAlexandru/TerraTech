package TerraTech.BranchManagementBackend.config;

import TerraTech.BranchManagementBackend.enums.Role;
import TerraTech.BranchManagementBackend.models.Product;
import TerraTech.BranchManagementBackend.models.Report;
import TerraTech.BranchManagementBackend.models.User;
import TerraTech.BranchManagementBackend.repositories.ProductRepository;
import TerraTech.BranchManagementBackend.repositories.ReportRepository;
import TerraTech.BranchManagementBackend.repositories.UserRepository;
import TerraTech.BranchManagementBackend.services.userServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ReportRepository reportRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0 && productRepository.count() == 0 && reportRepository.count() == 0) {

            List<User> listOfEmployees1 = new ArrayList<>();
            List<User> listOfEmployees2 = new ArrayList<>();
            List<Report> listOfReports1 = new ArrayList<>();
            List<Report> listOfReports2 = new ArrayList<>();
            //----------------------------------------------------------------------
            User admin = User.builder()
                    .firstName("admin~")
                    .lastName("~admin")
                    .phone("1929374999")
                    .email("ad@ad.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_ADMIN)
                    .createdAt(LocalDate.now())
                    .employees(new ArrayList<>())
                    .status(true)
                    .build();
            userRepository.save(admin);
            //----------------------------------------------------------------------manager 1 data
            User manager1 = User.builder()
                    .firstName("[Manager1]")
                    .lastName("Admin")
                    .phone("1928374651")
                    .email("adminManager1@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_MANAGER)
                    .createdAt(LocalDate.now())
                    .employees(listOfEmployees1)
                    .status(true)
                    .build();
            userRepository.save(manager1);
            for (int i = 1; i <= 12; i++) {
                String month = (i <= 9) ? String.format("%02d", i) : String.valueOf(i);
                String date = "2023-" + month;
                LocalDate localDate = LocalDate.parse(date + "-10");

                User employee = User.builder()
                        .firstName("M" + i + "hai")
                        .lastName("P" + i + "stol")
                        .phone("1234" + i + "6789")
                        .email("dummyEmployee" + i + "@admin.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ROLE_EMPLOYEE)
                        .createdAt(LocalDate.now())//.minusMonths(2)
                        .manager(manager1)
                        .status(true)
                        .build();
                listOfEmployees1.add(employee);
                userRepository.save(employee);
                Product product = Product.builder()
                        .name("Laptop Generation" + i)
                        .price(i * 100)
                        .producer("Asus")
                        .inStock(i + 54)
                        .addedAt(localDate)
                        .manager(manager1)
                        .reports(listOfReports1)
                        .build();
                productRepository.save(product);
                Report report = Report.builder()
                        .description("Employee " + i + "'s report")
                        .createDate(localDate)
                        .peopleNotifiedAboutProduct(i * 20)
                        .peopleSoldTo(i * 20)
                        .user(employee)
                        .product(product)
                        .build();
                listOfReports1.add(report);
                reportRepository.save(report);
            }
            //----------------------------------------------------------------------
            //----------------------------------------------------------------------manager 2 data
            User manager2 = User.builder()
                    .firstName("[Manager2]")
                    .lastName("Admin")
                    .phone("9988778899")
                    .email("adminManager2@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_MANAGER)
                    .createdAt(LocalDate.now())
                    .employees(listOfEmployees2)
                    .status(true)
                    .build();
            userRepository.save(manager2);
            for (int i = 1; i <= 3; i++) {
                String month = (i <= 9) ? String.format("%02d", i) : String.valueOf(i);
                String date = "2023-" + month;
                LocalDate localDate = LocalDate.parse(date + "-10");

                User employee = User.builder()
                        .firstName("V" + i + "ad")
                        .lastName("~(manager2)" + i)
                        .phone("2345" + i + "6789")
                        .email("dummyEmployeeManager2" + i + "@admin.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ROLE_EMPLOYEE)
                        .createdAt(LocalDate.now())
                        .manager(manager2)
                        .status(true)
                        .build();
                listOfEmployees2.add(employee);
                userRepository.save(employee);
                Product product = Product.builder()
                        .name("~Laptop Generation (manager2)" + i)
                        .price(9)
                        .producer("~Asus M2")
                        .inStock(9)
                        .addedAt(localDate)
                        .manager(manager2)
                        .reports(listOfReports2)
                        .build();
                productRepository.save(product);
                Report report = Report.builder()
                        .description("~Employee " + i + "'s report M2")
                        .createDate(localDate)
                        .peopleNotifiedAboutProduct(9)
                        .peopleSoldTo(9)
                        .user(employee)
                        .product(product)
                        .build();
                listOfReports1.add(report);
                reportRepository.save(report);
            }
        }
    }
}