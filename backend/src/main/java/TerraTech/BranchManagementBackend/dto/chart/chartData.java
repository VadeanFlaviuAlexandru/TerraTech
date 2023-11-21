package TerraTech.BranchManagementBackend.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
public class chartData {
    String name;
    Long value;

    public chartData(String name, Long value) {
        this.name = name;
        this.value = value;
    }
}
