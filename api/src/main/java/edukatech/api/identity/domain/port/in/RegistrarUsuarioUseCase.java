package edukatech.api.identity.domain.port.in;

import edukatech.api.identity.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {

    Usuario registrar(RegistrarUsuarioCommand command);

    record RegistrarUsuarioCommand(String institutionalCode, String email, String rawPassword, String fullName) {}
}
