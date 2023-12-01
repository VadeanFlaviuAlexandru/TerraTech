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
    private Long id;
    private Long productId;
    private String description;
    private String productName;
    private LocalDate createDate;
    private Integer peopleNotified;
    private Integer peopleSold;
}
