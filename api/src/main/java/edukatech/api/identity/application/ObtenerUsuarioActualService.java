package edukatech.api.identity.application;

import edukatech.api.identity.domain.exception.UsuarioNoEncontradoException;
import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.ObtenerUsuarioActualUseCase;
import edukatech.api.identity.domain.port.out.TransactionClaimsPort;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ObtenerUsuarioActualService implements ObtenerUsuarioActualUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final TransactionClaimsPort transactionClaimsPort;

    public ObtenerUsuarioActualService(UsuarioRepositoryPort usuarioRepositoryPort,
                                        TransactionClaimsPort transactionClaimsPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.transactionClaimsPort = transactionClaimsPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerActual(UUID idUsuarioAutenticado, Rol rolAutenticado) {
        transactionClaimsPort.aplicarClaims(idUsuarioAutenticado, rolAutenticado);
        return usuarioRepositoryPort.buscarPorId(idUsuarioAutenticado)
                .orElseThrow(UsuarioNoEncontradoException::new);
    }
}
