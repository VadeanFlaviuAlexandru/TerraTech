package TerraTech.BranchManagementBackend.dto.chart.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class dataRequest {
    String name;
    Long peopleNotifiedAboutProduct;
    Long peopleSoldTo;
}
