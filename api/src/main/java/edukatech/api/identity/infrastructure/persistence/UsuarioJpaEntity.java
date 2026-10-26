package edukatech.api.identity.infrastructure.persistence;

import edukatech.api.identity.domain.model.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "institutional_code", nullable = false, unique = true)
    private String institutionalCode;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", nullable = false, columnDefinition = "user_role")
    private Rol rol;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Instant updatedAt;

    protected UsuarioJpaEntity() {
    }

    public UsuarioJpaEntity(UUID id, String institutionalCode, String email, String passwordHash,
                             Rol rol, String fullName, String avatarUrl, boolean active) {
        this.id = id;
        this.institutionalCode = institutionalCode;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.active = active;
    }

    public UUID getId() { return id; }
    public String getInstitutionalCode() { return institutionalCode; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Rol getRol() { return rol; }
    public String getFullName() { return fullName; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "UsuarioJpaEntity[id=%s, email=%s, rol=%s]".formatted(id, email, rol);
    }
}