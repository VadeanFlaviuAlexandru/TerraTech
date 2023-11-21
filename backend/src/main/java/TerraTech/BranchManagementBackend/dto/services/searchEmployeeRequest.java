package TerraTech.BranchManagementBackend.dto.services;

import TerraTech.BranchManagementBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import TerraTech.BranchManagementBackend.dto.chart.user.chartRequest;

import TerraTech.BranchManagementBackend.dto.chart.user.reportRequest;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class searchEmployeeRequest {
    User user;
    List<User> listOfEmployees;
    chartRequest chartInfo;
    List<reportRequest> reports;
}
