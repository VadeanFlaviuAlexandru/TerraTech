package TerraTech.BranchManagementBackend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class signUpRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
}