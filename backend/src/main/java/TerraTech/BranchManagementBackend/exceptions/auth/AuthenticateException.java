package TerraTech.BranchManagementBackend.exceptions.auth;

public class AuthenticateException extends RuntimeException {
    public AuthenticateException(String message) {
        super("Invalid credentials! " + message);
    }
}