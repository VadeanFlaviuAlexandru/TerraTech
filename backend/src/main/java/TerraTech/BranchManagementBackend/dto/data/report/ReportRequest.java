package TerraTech.BranchManagementBackend.dto.data.report;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private String productId;
    @Column(length = 500)
    private String description;
    private int peopleNotified;
    private int peopleSold;
}
