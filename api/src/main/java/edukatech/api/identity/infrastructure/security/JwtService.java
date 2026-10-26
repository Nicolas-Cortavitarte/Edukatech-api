package edukatech.api.identity.infrastructure.security;

import edukatech.api.identity.domain.model.Rol;
import edukatech.api.identity.domain.port.out.TokenIssuerPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService implements TokenIssuerPort {

    private static final int MIN_SECRET_LENGTH = 32;

    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        String secret = properties.getSecret();
        if (secret == null || secret.length() < MIN_SECRET_LENGTH) {
            throw new IllegalStateException(
                    "jwt.secret debe estar configurado y tener al menos "
                            + MIN_SECRET_LENGTH + " caracteres (256 bits para HS256)");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public IssuedToken emitir(UUID userId, Rol rol) {
        Instant ahora = Instant.now();
        Instant expiracion = ahora.plusMillis(properties.getExpiration());

        String token = Jwts.builder()
                .subject(userId.toString())
                .claim("role", rol.name())
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expiracion))
                .signWith(signingKey)
                .compact();

        return new IssuedToken(token, properties.getExpiration() / 1000);
    }

    public ValidatedToken validar(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            UUID userId = UUID.fromString(claims.getSubject());
            Rol rol = Rol.valueOf(claims.get("role", String.class));
            return new ValidatedToken(userId, rol);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenInvalidoException("El token ha expirado");
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtTokenInvalidoException("Token inválido");
        }
    }

    public record ValidatedToken(UUID userId, Rol rol) {}
}