package TerraTech.BranchManagementBackend.exceptions.manager;

public class registerException extends RuntimeException {
    public registerException(String message) {
        super("Wait! " + message);
    }
}