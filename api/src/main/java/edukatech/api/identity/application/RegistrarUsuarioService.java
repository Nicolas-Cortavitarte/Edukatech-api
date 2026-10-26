package edukatech.api.identity.application;

import edukatech.api.identity.domain.exception.CodigoInstitucionalYaRegistradoException;
import edukatech.api.identity.domain.exception.CorreoYaRegistradoException;
import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.model.Usuario;
import edukatech.api.identity.domain.port.in.RegistrarUsuarioUseCase;
import edukatech.api.identity.domain.port.out.PasswordEncoderPort;
import edukatech.api.identity.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegistrarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                    PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    @Transactional
    public Usuario registrar(RegistrarUsuarioCommand command) {
        if (usuarioRepositoryPort.existePorEmail(command.email())) {
            throw new CorreoYaRegistradoException();
        }
        if (usuarioRepositoryPort.existePorCodigoInstitucional(command.institutionalCode())) {
            throw new CodigoInstitucionalYaRegistradoException();
        }

        String passwordHash = passwordEncoderPort.codificar(command.rawPassword());

        Usuario nuevoUsuario = new Usuario(
                UUID.randomUUID(),
                command.institutionalCode(),
                command.email(),
                passwordHash,
                Rol.alumno,
                command.fullName(),
                null,
                true,
                Instant.now()
        );

        return usuarioRepositoryPort.guardar(nuevoUsuario);
    }
}