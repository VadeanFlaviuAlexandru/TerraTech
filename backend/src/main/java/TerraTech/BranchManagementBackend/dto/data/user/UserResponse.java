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
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate createdAt;
    private String email;
    private String phone;
    private Role role;
    private Boolean status;
}
