package edukatech.api.identity.infrastructure.security;

import edukatech.api.identity.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String codificar(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean verificar(String rawPassword, String hash) {
        return passwordEncoder.matches(rawPassword, hash);
    }
}
