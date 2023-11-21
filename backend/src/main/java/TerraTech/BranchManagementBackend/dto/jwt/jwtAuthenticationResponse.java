package TerraTech.BranchManagementBackend.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class jwtAuthenticationResponse {
    String token;
}