package edukatech.api.identity.application;

import edukatech.api.identity.domain.exception.CredencialesInvalidasException;
import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.AutenticarUsuarioUseCase;
import edukatech.api.identity.domain.port.out.PasswordEncoderPort;
import edukatech.api.identity.domain.port.out.TokenIssuerPort;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticarUsuarioService implements AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenIssuerPort tokenIssuerPort;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                     PasswordEncoderPort passwordEncoderPort,
                                     TokenIssuerPort tokenIssuerPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenIssuerPort = tokenIssuerPort;
    }

    @Override
    @Transactional(readOnly = true)
    public SesionIniciada autenticar(String email, String rawPassword) {
        Usuario usuario = usuarioRepositoryPort.buscarPorEmail(email)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoderPort.verificar(rawPassword, usuario.passwordHash())) {
            throw new CredencialesInvalidasException();
        }
        if (!usuario.active()) {
            throw new CredencialesInvalidasException();
        }

        TokenIssuerPort.IssuedToken token = tokenIssuerPort.emitir(usuario.id(), usuario.rol());
        return new SesionIniciada(token.token(), token.expiresInSeconds(), usuario);
    }
}