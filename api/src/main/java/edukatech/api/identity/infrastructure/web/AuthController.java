package edukatech.api.identity.infrastructure.web;

import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.AutenticarUsuarioUseCase;
import edukatech.api.identity.domain.port.in.ObtenerUsuarioActualUseCase;
import edukatech.api.identity.domain.port.in.RegistrarUsuarioUseCase;
import edukatech.api.identity.infrastructure.security.UsuarioPrincipal;
import edukatech.api.identity.infrastructure.web.dto.LoginRequest;
import edukatech.api.identity.infrastructure.web.dto.RegistroRequest;
import edukatech.api.identity.infrastructure.web.dto.TokenResponse;
import edukatech.api.identity.infrastructure.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    private final ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase;

    public AuthController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                           AutenticarUsuarioUseCase autenticarUsuarioUseCase,
                           ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
        this.obtenerUsuarioActualUseCase = obtenerUsuarioActualUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegistroRequest request) {
        Usuario usuario = registrarUsuarioUseCase.registrar(new RegistrarUsuarioUseCase.RegistrarUsuarioCommand(
                request.institutionalCode(), request.email(), request.password(), request.fullName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.from(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        AutenticarUsuarioUseCase.SesionIniciada sesion =
                autenticarUsuarioUseCase.autenticar(request.email(), request.password());
        return ResponseEntity.ok(new TokenResponse(sesion.accessToken(), "Bearer", sesion.expiresInSeconds()));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(@AuthenticationPrincipal UsuarioPrincipal principal) {
        Usuario usuario = obtenerUsuarioActualUseCase.obtenerActual(principal.getId(), principal.getRol());
        return ResponseEntity.ok(UsuarioResponse.from(usuario));
    }
}
