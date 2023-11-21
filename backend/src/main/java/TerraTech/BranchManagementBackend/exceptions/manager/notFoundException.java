package TerraTech.BranchManagementBackend.exceptions.manager;

public class notFoundException extends RuntimeException {
    public notFoundException(Long id) {
        super("Error! Cannot find this id: " + id);
    }
}