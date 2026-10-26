package edukatech.api.identity.domain.port.out;

import edukatech.api.identity.domain.model.Rol;

import java.util.UUID;

public interface TokenIssuerPort {
    IssuedToken emitir(UUID userId, Rol rol);

    record IssuedToken(String token, long expiresInSeconds) {}
}
