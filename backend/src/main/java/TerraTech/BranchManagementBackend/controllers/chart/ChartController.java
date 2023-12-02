package TerraTech.BranchManagementBackend.controllers.chart;

import TerraTech.BranchManagementBackend.services.chart.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import TerraTech.BranchManagementBackend.dto.chart.StatisticsResponse;

@RestController
@RequestMapping("/TerraTechInc")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping("/chart/statistics/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_ADMIN')")
    public StatisticsResponse fetchStatistics(@PathVariable long id) {
        return chartService.fetchStatistics(id);
    }
}
