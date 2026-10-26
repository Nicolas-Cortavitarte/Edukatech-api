package edukatech.api.identity.infrastructure.security;

import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.port.out.TransactionClaimsPort;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

@Component
public class RequestClaimsPropagator implements TransactionClaimsPort {

    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    public RequestClaimsPropagator(EntityManager entityManager, ObjectMapper objectMapper) {
        this.entityManager = entityManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void aplicarClaims(UUID userId, Rol rol) {
        // Jackson 3: writeValueAsString no lanza checked exception.
        String claimsJson = objectMapper.writeValueAsString(
                Map.of("sub", userId.toString(), "role", rol.name()));

        entityManager.createNativeQuery("SELECT set_config('request.jwt.claims', ?1, true)")
                .setParameter(1, claimsJson)
                .getSingleResult();
    }
}