package edukatech.api.identity.domain.exception;

public class CorreoYaRegistradoException extends DomainException {
    public CorreoYaRegistradoException() {
        super("El correo ya está registrado");
    }
}