package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.PerfilService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/perfil")
@Tag(
        name = "Perfil",
        description = "Endpoints que gerenciam os perfis dos usuários. " +
                "Permite criar, atualizar, buscar e deletar perfis, bem como listar perfis de forma paginada. " +
                "Esses endpoints fornecem acesso a informações detalhadas e resumidas de perfis de usuários na " +
                "rede social.")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Operation(
            summary = "Buscar perfil por ID",
            description = "Retorna as informações completas de um perfil com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Perfil encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<PerfilDTO> getPerfilById(@Parameter(description = "ID do perfil", example = "1") @PathVariable Integer id) {
        PerfilDTO perfil = perfilService.getPerfilById(id);
        return new ServiceResponse<>(perfil, "Perfil encontrado", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Criar um novo perfil",
            description = "Cria um novo perfil para um usuário.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Perfil criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<PerfilDTO> savePerfil(@RequestBody @Valid Perfil perfil) {
        PerfilDTO perfilDTO = perfilService.savePerfil(perfil);
        return new ServiceResponse<>(perfilDTO, "Perfil salvo com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar perfil",
            description = "Deleta um perfil com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Perfil deletado com sucesso"
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerfil(@Parameter(description = "ID do perfil", example = "1") @PathVariable Integer id) {
        perfilService.deletePerfil(id);
    }

    @Operation(
            summary = "Atualizar perfil",
            description = "Atualiza as informações de um perfil com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Perfil atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> updatePerfil(
            @Parameter(description = "ID do perfil", example = "1") @PathVariable Integer id,
            @RequestBody @Valid Perfil perfil) {
        perfilService.updatePerfil(id, perfil);
        return new ServiceResponse<>(null, "Perfil atualizado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar perfis",
            description = "Retorna uma lista paginada de perfis.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Perfis encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum perfil encontrado")
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PerfilResumidoDTO>> getAllPerfil(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PerfilResumidoDTO> perfis = perfilService.listarPerfil(page, size);
        String mensagem = perfis.isEmpty() ? "Nenhum perfil encontrado" : "Perfis encontrados";
        return new ServiceResponse<>(perfis, mensagem, !perfis.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Buscar perfil por username",
            description = "Retorna as informações resumidas de um perfil com base no nome de usuário.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Perfil encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
            }
    )
    @GetMapping("/buscar/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<PerfilResumidoDTO> buscarPorUsername(@Parameter(description = "Nome de usuário", example = "johndoe") @PathVariable String username) {
        PerfilResumidoDTO perfilResumidoDTO = perfilService.buscarPorUsername(username);
        String mensagem = perfilResumidoDTO != null ? "Perfil encontrado" : "Perfil não encontrado";
        return new ServiceResponse<>(perfilResumidoDTO, mensagem, perfilResumidoDTO != null, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
