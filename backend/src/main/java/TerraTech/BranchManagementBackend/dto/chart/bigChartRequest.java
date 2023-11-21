package TerraTech.BranchManagementBackend.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class bigChartRequest {
    String name;
    List<chartData> top3;

    public bigChartRequest(String name, List<chartData> top3) {
        this.name = name;
        this.top3 = top3;
    }
}
