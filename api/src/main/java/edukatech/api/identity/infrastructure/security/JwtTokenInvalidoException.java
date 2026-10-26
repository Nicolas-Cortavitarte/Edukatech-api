package edukatech.api.identity.infrastructure.security;

public class JwtTokenInvalidoException extends RuntimeException {
    public JwtTokenInvalidoException(String message) {
        super(message);
    }
}
