package TerraTech.BranchManagementBackend.dto.data.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long id;
    private String description;
    private LocalDate createDate;
    private Integer peopleNotifiedAboutProduct;
    private Integer peopleSoldTo;
    private String productName;
}
