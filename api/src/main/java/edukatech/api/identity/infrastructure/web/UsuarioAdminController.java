package edukatech.api.identity.infrastructure.web;

import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.ListarUsuariosUseCase;
import edukatech.api.identity.infrastructure.security.UsuarioPrincipal;
import edukatech.api.identity.infrastructure.web.dto.UsuarioResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioAdminController {

    private final ListarUsuariosUseCase listarUsuariosUseCase;

    public UsuarioAdminController(ListarUsuariosUseCase listarUsuariosUseCase) {
        this.listarUsuariosUseCase = listarUsuariosUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<UsuarioResponse> listar(@AuthenticationPrincipal UsuarioPrincipal principal) {
        List<Usuario> usuarios = listarUsuariosUseCase.listar(principal.getId(), principal.getRol());
        return usuarios.stream().map(UsuarioResponse::from).toList();
    }
}
