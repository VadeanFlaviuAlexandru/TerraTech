package TerraTech.BranchManagementBackend.dto.chart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChartData {
    private String name;
    private long value;

    public ChartData(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
