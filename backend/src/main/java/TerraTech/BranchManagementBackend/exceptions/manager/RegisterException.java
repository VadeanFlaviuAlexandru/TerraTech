package TerraTech.BranchManagementBackend.exceptions.manager;

public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super("Wait! " + message);
    }
}