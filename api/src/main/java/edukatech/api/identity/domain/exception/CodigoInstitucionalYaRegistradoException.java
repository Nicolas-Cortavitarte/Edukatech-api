package edukatech.api.identity.domain.exception;

public class CodigoInstitucionalYaRegistradoException extends DomainException {
    public CodigoInstitucionalYaRegistradoException() {
        super("El código institucional ya está registrado");
    }
}