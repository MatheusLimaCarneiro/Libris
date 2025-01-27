package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.CredenciaisDTO;
import prati.projeto.redeSocial.rest.dto.TokenDTO;
import prati.projeto.redeSocial.security.JwtService;
import prati.projeto.redeSocial.service.UsuarioService;

@RestController
@AllArgsConstructor
@RequestMapping("libris/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.saveUsuario(usuario);
    }

    @PostMapping("/login")
    public TokenDTO authenticate(@RequestBody @Valid CredenciaisDTO credenciais) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        credenciais.getLogin(),
                        credenciais.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        Usuario usuario = Usuario.builder()
                .username(credenciais.getLogin())
                .senha(credenciais.getSenha())
                .build();

        String token = jwtService.gerarToken(usuario);

        return new TokenDTO(credenciais.getLogin(), token);
    }
}
