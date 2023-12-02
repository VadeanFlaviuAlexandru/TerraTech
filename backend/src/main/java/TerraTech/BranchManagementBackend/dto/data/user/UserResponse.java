package TerraTech.BranchManagementBackend.dto.data.user;

import TerraTech.BranchManagementBackend.enums.Role;

import java.time.LocalDate;

public record UserResponse(
        long id,
        String firstName,
        String lastName,
        String phone,
        boolean status,
        String email,
        LocalDate createdAt,
        Role role
) {
}