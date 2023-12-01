package TerraTech.BranchManagementBackend.dto.data.product;

import TerraTech.BranchManagementBackend.models.User;

import java.time.LocalDate;

public record ProductResponse(
        Long id,
        String name,
        String producer,
        LocalDate addedAt,
        Integer inStock,
        Integer price,
        Integer numberOfReports,
        User manager
) {
}