package TerraTech.BranchManagementBackend.dto.data.user;

import java.time.LocalDate;

public record ManagerRequest(
        long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate createdAt,
        boolean status
) {
}
