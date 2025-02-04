package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.CredenciaisDTO;
import prati.projeto.redeSocial.rest.dto.TokenDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.security.JwtService;
import prati.projeto.redeSocial.service.UsuarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public ApiResponse<Usuario> saveUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
        return new ApiResponse<>(usuarioSalvo, "Usuário registrado com sucesso", true, getFormattedTimestamp());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TokenDTO> authenticate(@RequestBody @Valid CredenciaisDTO credenciais) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        credenciais.getLogin(),
                        credenciais.getSenha()
                );

        authenticationManager.authenticate(authenticationToken);

        Usuario usuario = Usuario.builder()
                .username(credenciais.getLogin())
                .build();

        TokenDTO tokenDTO = jwtService.gerarTokensParaUsuario(usuario);
        return new ApiResponse<>(tokenDTO, "Login bem-sucedido", true, getFormattedTimestamp());
    }


    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
