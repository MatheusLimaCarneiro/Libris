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
import prati.projeto.redeSocial.rest.dto.ComentarioForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/posts/{postId}/comentarios")
@Tag(
        name = "Comentários do Fórum",
        description = "Endpoints para gerenciar comentários em posts do fórum. " +
                "Permite criar, listar, buscar e deletar comentários associados a um post específico."
)
public class ComentarioForumController {

    @Autowired
    private ComentarioForumService comentarioForumService;

    @Operation(
            summary = "Criar Comentário",
            description = "Cria um novo comentário em um post específico. O comentário deve conter o ID do perfil e o texto.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário criado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos"),
                    @ApiResponse(responseCode = "404", description = "Post não encontrado")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ComentarioForumResponseDTO> criarComentario(
            @Parameter(description = "ID do post ao qual o comentário será adicionado", example = "1", required = true)
            @PathVariable Integer postId,
            @Parameter(description = "DTO contendo os dados do comentário", required = true)
            @RequestBody @Valid ComentarioForumRequestDTO dto) {

        ComentarioForumResponseDTO comentarioDTO = comentarioForumService.criarComentario(postId, dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário criado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Comentários por Post",
            description = "Retorna uma lista paginada de comentários associados a um post específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários encontrados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado para o post")
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioForumResponseDTO>> listarComentariosPorPost(
            @Parameter(description = "ID do post para listar os comentários", example = "1", required = true)
            @PathVariable Integer postId,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioForumResponseDTO> comentariosPage = comentarioForumService.listarPorPost(postId, page, size);
        String mensagem = comentariosPage.isEmpty() ? "Nenhum comentário encontrado" : "Comentários encontrados";
        return new ServiceResponse<>(comentariosPage, mensagem, !comentariosPage.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar Comentário",
            description = "Deleta um comentário específico pelo seu ID, desde que pertença ao post informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Comentário ou post não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarComentario(
            @Parameter(description = "ID do post ao qual o comentário pertence", example = "1", required = true)
            @PathVariable Integer postId,
            @Parameter(description = "ID do comentário a ser deletado", example = "1", required = true)
            @PathVariable Integer id) {
        comentarioForumService.deletarComentario(postId, id);
    }

    @Operation(
            summary = "Buscar Comentário por ID",
            description = "Busca um comentário específico pelo seu ID, desde que pertença ao post informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário encontrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Comentário ou post não encontrado")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioForumResponseDTO> buscarComentarioPorId(
            @Parameter(description = "ID do post ao qual o comentário pertence", example = "1", required = true)
            @PathVariable Integer postId,
            @Parameter(description = "ID do comentário a ser buscado", example = "1", required = true)
            @PathVariable Integer id) {

        ComentarioForumResponseDTO comentarioDTO = comentarioForumService.buscarComentario(postId, id);
        return new ServiceResponse<>(comentarioDTO, "Comentário encontrado", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Comentários por Username",
            description = "Retorna uma lista paginada de comentários associados a um post específico, feitos por um usuário específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários encontrados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado para o usuário")
            }
    )
                    @GetMapping("/usuario/{username}")
                    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioForumResponseDTO>> listarComentariosPorUsername(
            @Parameter(description = "ID do post ao qual os comentários pertencem", example = "1", required = true)
            @PathVariable Integer postId,
            @Parameter(description = "Username do usuário que fez os comentários", example = "usuario123", required = true)
            @PathVariable String username,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioForumResponseDTO> comentariosPage = comentarioForumService.listarComentariosPorUsername(username, postId, page, size);
        String mensagem = comentariosPage.isEmpty() ? "Nenhum comentário encontrado para este usuário" : "Comentários encontrados para este usuário";
        return new ServiceResponse<>(comentariosPage, mensagem, !comentariosPage.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}