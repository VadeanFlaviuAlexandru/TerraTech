package TerraTech.BranchManagementBackend.utils;

public class ExtractToken {
    private static final String TOKEN_PREFIX = "Bearer ";

    public static String extractToken(String token) {
        if (token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }
    }
}

