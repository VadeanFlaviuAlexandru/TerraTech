package TerraTech.BranchManagementBackend.exceptions.manager;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Error! Cannot find this id: " + id);
    }
}