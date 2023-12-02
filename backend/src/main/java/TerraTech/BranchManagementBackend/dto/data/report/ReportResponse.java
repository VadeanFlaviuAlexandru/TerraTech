package TerraTech.BranchManagementBackend.dto.data.report;

import java.time.LocalDate;

public record ReportResponse(long id, String description, LocalDate createDate, int peopleNotified,
                             int peopleSold, String productName) {
}