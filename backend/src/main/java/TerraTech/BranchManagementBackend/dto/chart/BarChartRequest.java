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
public class BarChartRequest {
    String title;
    String color;
    String dataKey;
    List<ChartData> chartData;
}
