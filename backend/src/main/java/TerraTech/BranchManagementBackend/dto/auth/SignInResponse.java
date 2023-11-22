package TerraTech.BranchManagementBackend.dto.auth;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
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
public class SignInResponse {
    String token;
    User user;
    ChartRequest chartInfo;
    List<ReportRequest> reports;
}
