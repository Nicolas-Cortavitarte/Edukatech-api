package edukatech.api.identity.domain.port.out;

public interface PasswordEncoderPort {
    String codificar(String rawPassword);

    boolean verificar(String rawPassword, String hash);
}
