package edukatech.api.identity.infrastructure.persistence;

import edukatech.api.identity.domain.model.Usuario;

final class UsuarioMapper {

    private UsuarioMapper() {
    }

    static Usuario toDomain(UsuarioJpaEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getInstitutionalCode(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRol(),
                entity.getFullName(),
                entity.getAvatarUrl(),
                entity.isActive(),
                entity.getCreatedAt()
        );
    }

    static UsuarioJpaEntity toNewEntity(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.id(),
                usuario.institutionalCode(),
                usuario.email(),
                usuario.passwordHash(),
                usuario.rol(),
                usuario.fullName(),
                usuario.avatarUrl(),
                usuario.active()
        );
    }
}
