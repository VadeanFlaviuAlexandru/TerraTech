package TerraTech.BranchManagementBackend.dto.services;

import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;
import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;
import TerraTech.BranchManagementBackend.models.User;

import java.util.List;

public record SearchEmployeeResponse(
        User user,
        List<User> listOfEmployees,
        ChartRequest chartInfo,
        List<ReportRequest> reports) {
}
