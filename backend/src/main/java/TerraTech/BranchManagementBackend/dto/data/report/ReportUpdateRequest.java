package TerraTech.BranchManagementBackend.dto.data.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportUpdateRequest {
    private String productId;
    private String description;
    private Integer peopleNotified;
    private Integer peopleSold;
}