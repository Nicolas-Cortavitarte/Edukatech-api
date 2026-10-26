package edukatech.api.identity.infrastructure.security;

import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioRepositoryPort usuarioRepositoryPort) {
        this.jwtService = jwtService;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            try {
                JwtService.ValidatedToken validado = jwtService.validar(token);
                Optional<Usuario> usuario = usuarioRepositoryPort.buscarPorId(validado.userId());

                if (usuario.isPresent() && usuario.get().active()) {
                    UsuarioPrincipal principal = new UsuarioPrincipal(
                            usuario.get().id(), usuario.get().email(), usuario.get().rol(), true);
                    var authentication = new UsernamePasswordAuthenticationToken(
                            principal, null, principal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    log.debug("Token válido pero el usuario no existe o está inactivo");
                }
            } catch (JwtTokenInvalidoException e) {
                // Nunca se loguea el valor del token, solo el motivo del rechazo.
                log.debug("Token JWT rechazado: {}", e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
