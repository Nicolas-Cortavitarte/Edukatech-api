package edukatech.api.identity.domain.exception;

public class CredencialesInvalidasException extends DomainException {
    public CredencialesInvalidasException() {
        super("Credenciales inválidas");
    }
}