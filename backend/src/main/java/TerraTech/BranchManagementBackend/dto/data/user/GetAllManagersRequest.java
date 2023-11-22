package TerraTech.BranchManagementBackend.dto.data.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllManagersRequest {
    private List<ManagerRequest> managers;
}
