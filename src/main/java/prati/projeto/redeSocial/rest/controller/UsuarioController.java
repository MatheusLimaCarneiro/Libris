package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import prati.projeto.redeSocial.modal.entity.ResetPasswordRequest;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.ChangePasswordDTO;
import prati.projeto.redeSocial.rest.dto.ResetPasswordDTO;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.UsuarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/usuario")
@Tag(name = "Usuários", description = "Gerenciamento de usuários na rede social")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Buscar usuário por email",
            description = "Retorna as informações resumidas de um usuário com base no seu email.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @GetMapping("{email}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<UsuarioResumidoDTO> getUsuarioByEmail(
            @Parameter(description = "Email do usuário", example = "usuario@example.com")
            @PathVariable String email) {
        UsuarioResumidoDTO usuarioResumido = usuarioService.getUsuarioByEmail(email);
        return new ServiceResponse<>(usuarioResumido, "Usuário encontrado", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Deleta um usuário com base no seu email.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuário deletado com sucesso"
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @DeleteMapping("{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(
            @Parameter(description = "Email do usuário", example = "usuario@example.com")
            @PathVariable String email) {
        usuarioService.deleteUsuario(email);
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza as informações de um usuário com base no seu email.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PutMapping("{email}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<UsuarioResumidoDTO> updateUsuario(
            @Parameter(description = "Email do usuário", example = "usuario@example.com")
            @PathVariable String email,
            @RequestBody @Valid Usuario usuario) {
        usuarioService.updateUsuario(email, usuario);
        UsuarioResumidoDTO usuarioResumido = new UsuarioResumidoDTO(usuario.getUsername(), usuario.getEmail());
        return new ServiceResponse<>(usuarioResumido, "Usuário atualizado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Solicitar mudança de senha",
            description = "Envia um e-mail com um link para redefinir a senha.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "E-mail enviado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<String> requestPasswordReset(@RequestBody ResetPasswordRequest request) {
        usuarioService.requestPasswordReset(request.getEmail());
        return new ServiceResponse<>(null,"E-mail enviado com sucesso", true, getFormattedTimestamp());
    }


    @Operation(
            summary = "Confirmar redefinição de senha",
            description = "Redefine a senha do usuário utilizando um token válido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Senha redefinida com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Token inválido ou expirado")
            }
    )
    @PostMapping("/reset-password/confirm")
    public ServiceResponse<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        usuarioService.resetPassword(resetPasswordDTO.getToken(), resetPasswordDTO.getNewPassword());
        return new ServiceResponse<>(null,"Senha redefinida com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Alterar senha",
            description = "Altera a senha de um usuário autenticado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Senha alterada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Senha atual inválida")
            }
    )
    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<String> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                                  Authentication authentication) {
        String email = authentication.getName();
        usuarioService.changePassword(email,
                changePasswordDTO.getOldPassword(),
                changePasswordDTO.getNewPassword());
        return new ServiceResponse<>(null, "Senha alterada com sucesso", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}