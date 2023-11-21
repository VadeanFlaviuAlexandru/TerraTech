package TerraTech.BranchManagementBackend.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class chartBoxRequest {
    Long number;
    String dataKey;
    String title;
    Double percentage;
    List<chartData> chartData;
}
