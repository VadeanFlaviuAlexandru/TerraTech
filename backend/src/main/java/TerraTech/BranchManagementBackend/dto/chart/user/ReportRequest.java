package TerraTech.BranchManagementBackend.dto.chart.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    Long id;
    String description;
    String productName;
    LocalDate createDate;
    Integer peopleNotifiedAboutProduct;
    Integer peopleSoldTo;
}
