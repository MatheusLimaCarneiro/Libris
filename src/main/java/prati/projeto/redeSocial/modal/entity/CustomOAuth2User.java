package prati.projeto.redeSocial.modal.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final Usuario usuario;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return usuario != null ? usuario.getUsername() : "unknown";
    }

    public String getUsername() {
        return usuario != null ? usuario.getUsername() : "unknown";
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
