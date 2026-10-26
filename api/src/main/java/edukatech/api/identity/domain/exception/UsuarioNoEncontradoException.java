package edukatech.api.identity.domain.exception;

public class UsuarioNoEncontradoException extends DomainException {
    public UsuarioNoEncontradoException() {
        super("Usuario no encontrado");
    }
}