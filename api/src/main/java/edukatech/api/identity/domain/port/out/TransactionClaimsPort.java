package edukatech.api.identity.domain.port.out;

import edukatech.api.identity.domain.model.Rol;

import java.util.UUID;

public interface TransactionClaimsPort {
    void aplicarClaims(UUID userId, Rol rol);
}
