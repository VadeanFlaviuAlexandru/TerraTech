package TerraTech.BranchManagementBackend.dto.data.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    String product_id;
    String description;
    Integer peopleNotifiedAboutProduct;
    Integer peopleSoldTo;
}
