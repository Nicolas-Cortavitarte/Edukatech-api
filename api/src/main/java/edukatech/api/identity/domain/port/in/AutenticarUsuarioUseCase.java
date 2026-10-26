package edukatech.api.identity.domain.port.in;

import edukatech.api.identity.domain.model.Usuario;

public interface AutenticarUsuarioUseCase {

    SesionIniciada autenticar(String email, String rawPassword);

    record SesionIniciada(String accessToken, long expiresInSeconds, Usuario usuario) {}
}
