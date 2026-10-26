package edukatech.api.identity.infrastructure.web;

import edukatech.api.identity.domain.exception.CodigoInstitucionalYaRegistradoException;
import edukatech.api.identity.domain.exception.CorreoYaRegistradoException;
import edukatech.api.identity.domain.exception.CredencialesInvalidasException;
import edukatech.api.identity.domain.exception.UsuarioInactivoException;
import edukatech.api.identity.domain.exception.UsuarioNoEncontradoException;
import edukatech.api.identity.infrastructure.security.JwtTokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errores.put(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(base(HttpStatus.BAD_REQUEST, "Datos inválidos", errores));
    }

    @ExceptionHandler({CorreoYaRegistradoException.class, CodigoInstitucionalYaRegistradoException.class})
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(base(HttpStatus.CONFLICT, ex.getMessage(), null));
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> handleCredenciales(CredencialesInvalidasException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(base(HttpStatus.UNAUTHORIZED, "Credenciales inválidas", null));
    }

    @ExceptionHandler(UsuarioInactivoException.class)
    public ResponseEntity<Map<String, Object>> handleInactivo(UsuarioInactivoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(base(HttpStatus.FORBIDDEN, "Usuario inactivo", null));
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNoEncontrado(UsuarioNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(base(HttpStatus.NOT_FOUND, "Usuario no encontrado", null));
    }

    @ExceptionHandler(JwtTokenInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleJwt(JwtTokenInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(base(HttpStatus.UNAUTHORIZED, "Token inválido o expirado", null));
    }

    private Map<String, Object> base(HttpStatus status, String message, Map<String, String> detalles) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", message);
        if (detalles != null && !detalles.isEmpty()) {
            body.put("detalles", detalles);
        }
        return body;
    }
}
