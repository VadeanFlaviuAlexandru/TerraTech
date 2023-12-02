package TerraTech.BranchManagementBackend.dto.data.product;

import TerraTech.BranchManagementBackend.models.User;

import java.time.LocalDate;

public record ProductResponse(
        long id,
        String name,
        String producer,
        LocalDate addedAt,
        int inStock,
        int price,
        int numberOfReports,
        User manager
) {
}