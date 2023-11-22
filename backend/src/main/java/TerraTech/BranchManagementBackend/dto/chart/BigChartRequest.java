package TerraTech.BranchManagementBackend.dto.chart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BigChartRequest {
    private String name;
    private List<ChartData> top3;

    public BigChartRequest(String name, List<ChartData> top3) {
        this.name = name;
        this.top3 = top3;
    }
}
