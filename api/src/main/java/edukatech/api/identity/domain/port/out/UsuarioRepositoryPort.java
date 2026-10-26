package edukatech.api.identity.domain.port.out;

import edukatech.api.identity.domain.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorId(UUID id);

    Optional<Usuario> buscarPorEmail(String email);

    boolean existePorEmail(String email);

    boolean existePorCodigoInstitucional(String institutionalCode);

    List<Usuario> listarTodos();
}
