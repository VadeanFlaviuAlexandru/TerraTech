package TerraTech.BranchManagementBackend.dto.auth;

import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;
import TerraTech.BranchManagementBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class signInResponse {
    String token;
    User user;
    chartRequest chartInfo;
    List<reportRequest> reports;
}
