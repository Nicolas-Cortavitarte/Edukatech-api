package edukatech.api.identity.infrastructure.web.dto;

import edukatech.api.identity.domain.model.Usuario;

import java.time.Instant;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String institutionalCode,
        String email,
        String role,
        String fullName,
        String avatarUrl,
        boolean active,
        Instant createdAt
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.id(),
                usuario.institutionalCode(),
                usuario.email(),
                usuario.rol().name(),
                usuario.fullName(),
                usuario.avatarUrl(),
                usuario.active(),
                usuario.createdAt()
        );
    }
}
