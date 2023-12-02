package TerraTech.BranchManagementBackend.dto.auth;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
import TerraTech.BranchManagementBackend.models.User;

import java.util.List;

public record SignInResponse(
        String token,
        User user,
        long managerId,
        ChartRequest chartInfo,
        List<ReportRequest> reports
) {
}