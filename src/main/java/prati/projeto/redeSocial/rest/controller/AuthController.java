package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.CredenciaisDTO;
import prati.projeto.redeSocial.rest.dto.TokenDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.security.JwtService;
import prati.projeto.redeSocial.service.UsuarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
@RequestMapping("libris/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados ao registro e login de usuários.")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(
            summary = "Registro de Usuário",
            description = "Registra um novo usuário no sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário registrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição mal formada")
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Usuario> saveUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        Usuario usuarioSalvo = usuarioService.saveUsuario(usuario);
        return new ServiceResponse<>(usuarioSalvo, "Usuário registrado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Login de Usuário",
            description = "Realiza o login de um usuário e retorna um token JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login bem-sucedido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<TokenDTO> authenticate(@RequestBody @Valid CredenciaisDTO credenciais) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        credenciais.getLogin(),
                        credenciais.getSenha()
                );

        authenticationManager.authenticate(authenticationToken);

        Usuario usuario = usuarioRepository.findByEmail(credenciais.getLogin())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        TokenDTO tokenDTO = jwtService.gerarTokensParaUsuario(usuario);
        return new ServiceResponse<>(tokenDTO, "Login bem-sucedido", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
