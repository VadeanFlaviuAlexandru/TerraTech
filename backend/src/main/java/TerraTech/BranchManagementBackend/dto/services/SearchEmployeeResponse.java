package TerraTech.BranchManagementBackend.dto.services;

import TerraTech.BranchManagementBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import TerraTech.BranchManagementBackend.dto.chart.user.ChartRequest;

import TerraTech.BranchManagementBackend.dto.chart.user.ReportRequest;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchEmployeeResponse {
    User user;
    List<User> listOfEmployees;
    ChartRequest chartInfo;
    List<ReportRequest> reports;
}
