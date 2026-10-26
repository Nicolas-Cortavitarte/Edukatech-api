package edukatech.api.identity.domain.exception;

public class UsuarioInactivoException extends DomainException {
    public UsuarioInactivoException() {
        super("El usuario está inactivo");
    }
}