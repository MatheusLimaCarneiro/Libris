package prati.projeto.redeSocial.service.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.modal.entity.CustomOAuth2User;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.service.UsuarioService;

@AllArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        Usuario usuario = usuarioRepository.findByEmail(email).orElseGet(() -> {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email);
            novoUsuario.setUsername(generateUsername(name, email));
            novoUsuario.setSenha(null);
            novoUsuario.setAuthProvider("google");
            novoUsuario.setAdmin(false);
            return usuarioService.saveUsuario(novoUsuario);
        });

        return new CustomOAuth2User(usuario);
    }

    private String generateUsername(String name, String email) {
        String username = name != null ? name : email.split("@")[0];
        if (usuarioRepository.existsByUsername(username)) {
            username = username + "_" + System.currentTimeMillis();
        }
        return username;
    }
}