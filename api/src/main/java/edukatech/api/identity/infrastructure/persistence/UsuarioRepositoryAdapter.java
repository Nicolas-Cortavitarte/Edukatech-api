package edukatech.api.identity.infrastructure.persistence;

import edukatech.api.identity.domain.exception.CodigoInstitucionalYaRegistradoException;
import edukatech.api.identity.domain.exception.CorreoYaRegistradoException;
import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioSpringDataRepository repository;

    public UsuarioRepositoryAdapter(UsuarioSpringDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        try {
            UsuarioJpaEntity guardado = repository.save(UsuarioMapper.toNewEntity(usuario));
            return UsuarioMapper.toDomain(guardado);
        } catch (DataIntegrityViolationException e) {
            throw traducirViolacion(e);
        }
    }

    private RuntimeException traducirViolacion(DataIntegrityViolationException e) {
        String detalle = e.getMostSpecificCause().getMessage();
        if (detalle != null) {
            if (detalle.contains("email")) {
                return new CorreoYaRegistradoException();
            }
            if (detalle.contains("institutional_code")) {
                return new CodigoInstitucionalYaRegistradoException();
            }
        }
        return e;
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return repository.findById(id).map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email).map(UsuarioMapper::toDomain);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existePorCodigoInstitucional(String institutionalCode) {
        return repository.existsByInstitutionalCode(institutionalCode);
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll().stream().map(UsuarioMapper::toDomain).toList();
    }
}