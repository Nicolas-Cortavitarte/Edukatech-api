package edukatech.api.identity.domain.port.in;

import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.model.Usuario;

import java.util.UUID;

public interface ObtenerUsuarioActualUseCase {
    Usuario obtenerActual(UUID idUsuarioAutenticado, Rol rolAutenticado);
}
