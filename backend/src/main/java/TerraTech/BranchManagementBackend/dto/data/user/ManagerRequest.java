package TerraTech.BranchManagementBackend.dto.data.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ManagerRequest {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phone;
    LocalDate createdAt;
    Boolean status;
}
