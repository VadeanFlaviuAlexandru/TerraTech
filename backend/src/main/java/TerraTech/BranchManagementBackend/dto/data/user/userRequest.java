package TerraTech.BranchManagementBackend.dto.data.user;

import TerraTech.BranchManagementBackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class userRequest {
    Optional<String> firstName;
    Optional<String> lastName;
    Optional<String> email;
    Optional<String> phone;
    Optional<Role> role;
}