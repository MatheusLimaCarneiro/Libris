package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ComentarioDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios")
@Tag(
        name = "Comentários",
        description = "Endpoints responsáveis pela gestão de comentários."
)
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;



    @Operation(
            summary = "Criar comentário",
            description = "Cria um novo comentário e retorna os detalhes do comentário criado.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário criado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do comentário.")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ComentarioDTO> saveComentario(@RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.salvar(dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário criado com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Buscar comentário por ID",
            description = "Retorna os detalhes do comentário com base no identificador fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário encontrado.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado.")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioDTO> buscarPorId(@PathVariable Integer id) {
        ComentarioDTO comentarioDTO = comentarioService.buscarPorId(id);
        return new ServiceResponse<>(comentarioDTO, "Comentário encontrado", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Listar todos os comentários",
            description = "Lista todos os comentários com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários encontrados.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioDTO> comentarios = comentarioService.listarTodos(page, size);
        String mensagem = comentarios.isEmpty() ? "Nenhum comentário encontrado" : "Comentários encontrados";
        return new ServiceResponse<>(comentarios, mensagem, !comentarios.isEmpty(), getFormattedTimestamp());
    }



    @Operation(
            summary = "Atualizar comentário",
            description = "Atualiza os dados de um comentário existente e retorna os detalhes atualizados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentário atualizado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado.")
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioDTO> atualizarComentario(@PathVariable Integer id,
                                                              @RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.atualizarComentario(id, dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário atualizado com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Listar comentários por livro",
            description = "Lista os comentários de um livro específico, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Comentários encontrados para este livro.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
            }
    )
    @GetMapping("/listar/livro/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioDTO>> listarPorLivro(
            @PathVariable Integer livroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioDTO> comentarios = comentarioService.listarPorLivro(livroId, page, size);
        String mensagem = comentarios.isEmpty() ? "Nenhum comentário encontrado para este livro" : "Comentários encontrados para este livro";
        return new ServiceResponse<>(comentarios, mensagem, !comentarios.isEmpty(), getFormattedTimestamp());
    }



    @Operation(
            summary = "Excluir comentário",
            description = "Exclui o comentário com base no identificador fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Comentário excluído com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado.")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirComentario(@PathVariable Integer id) {
        comentarioService.excluirComentario(id);
    }



    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}