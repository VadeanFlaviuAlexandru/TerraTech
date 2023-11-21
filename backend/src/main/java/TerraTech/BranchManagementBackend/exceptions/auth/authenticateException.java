package TerraTech.BranchManagementBackend.exceptions.auth;

public class authenticateException extends RuntimeException {
    public authenticateException(String message) {
        super("Invalid credentials! " + message);
    }
}