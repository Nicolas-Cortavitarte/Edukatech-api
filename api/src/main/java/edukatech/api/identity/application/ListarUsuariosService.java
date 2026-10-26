package edukatech.api.identity.application;

import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.ListarUsuariosUseCase;
import edukatech.api.identity.domain.port.out.TransactionClaimsPort;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ListarUsuariosService implements ListarUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final TransactionClaimsPort transactionClaimsPort;

    public ListarUsuariosService(UsuarioRepositoryPort usuarioRepositoryPort,
                                  TransactionClaimsPort transactionClaimsPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.transactionClaimsPort = transactionClaimsPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar(UUID idSolicitante, Rol rolSolicitante) {
        transactionClaimsPort.aplicarClaims(idSolicitante, rolSolicitante);
        return usuarioRepositoryPort.listarTodos();
    }
}
