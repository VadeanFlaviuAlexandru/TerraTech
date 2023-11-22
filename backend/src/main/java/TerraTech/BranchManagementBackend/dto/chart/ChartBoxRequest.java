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
public class ChartBoxRequest {
    private Long number;
    private String dataKey;
    private String title;
    private Double percentage;
    private List<ChartData> chartData;
}
