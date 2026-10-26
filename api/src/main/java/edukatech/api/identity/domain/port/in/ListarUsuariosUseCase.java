package edukatech.api.identity.domain.port.in;

import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.model.Usuario;

import java.util.List;
import java.util.UUID;

public interface ListarUsuariosUseCase {
    List<Usuario> listar(UUID idSolicitante, Rol rolSolicitante);
}
