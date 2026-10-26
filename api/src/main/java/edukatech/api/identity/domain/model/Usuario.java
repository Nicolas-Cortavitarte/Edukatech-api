package edukatech.api.identity.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Usuario(
        UUID id,
        String institutionalCode,
        String email,
        String passwordHash,
        Rol rol,
        String fullName,
        String avatarUrl,
        boolean active,
        Instant createdAt
) {

    public Usuario {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(institutionalCode, "institutionalCode");
        Objects.requireNonNull(email, "email");
        Objects.requireNonNull(passwordHash, "passwordHash");
        Objects.requireNonNull(rol, "rol");
        Objects.requireNonNull(fullName, "fullName");
        Objects.requireNonNull(createdAt, "createdAt");
        if (institutionalCode.isBlank()) {
            throw new IllegalArgumentException("institutionalCode no puede estar vacío");
        }
        if (email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("email inválido");
        }
    }

    @Override
    public String toString() {
        return "Usuario[id=%s, institutionalCode=%s, email=%s, rol=%s, active=%s]"
                .formatted(id, institutionalCode, email, rol, active);
    }
}