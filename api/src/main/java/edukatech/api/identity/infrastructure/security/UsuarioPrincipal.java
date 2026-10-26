package edukatech.api.identity.infrastructure.security;

import edukatech.api.identity.domain.model.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public class UsuarioPrincipal implements UserDetails {

    private final UUID id;
    private final String email;
    private final Rol rol;
    private final boolean active;

    public UsuarioPrincipal(UUID id, String email, Rol rol, boolean active) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.active = active;
    }

    public UUID getId() { return id; }

    public Rol getRol() { return rol; }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return active; }
}
