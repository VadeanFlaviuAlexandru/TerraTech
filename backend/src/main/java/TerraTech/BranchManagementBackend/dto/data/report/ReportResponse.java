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
    Long id;
    String description;
    LocalDate createDate;
    Integer peopleNotifiedAboutProduct;
    Integer peopleSoldTo;
}
