package TerraTech.BranchManagementBackend.dto.data.user;

import TerraTech.BranchManagementBackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    LocalDate createdAt;
    String email;
    String phone;
    Role role;
    Boolean status;
}
