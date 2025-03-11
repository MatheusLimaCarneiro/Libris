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
import prati.projeto.redeSocial.rest.dto.RespostaForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.RespostaForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios/{comentarioForumId}/respostas-forum")
@Tag(
        name = "Respostas do Fórum",
        description = "Endpoints para gerenciar respostas a comentários no fórum. " +
                "Permite criar, listar, buscar e deletar respostas associadas a um comentário específico."
)
public class RespostaForumController {

    @Autowired
    private RespostaForumService respostaForumService;

    @Operation(
            summary = "Criar Resposta",
            description = "Cria uma nova resposta a um comentário específico. A resposta deve conter o ID do perfil e o texto.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resposta criada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )

                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos"),
                    @ApiResponse(responseCode = "404", description = "Comentário não encontrado")
            }
    )
                    @PostMapping
                    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<RespostaForumResponseDTO> criarRespostaForum(
            @Parameter(description = "ID do comentário ao qual a resposta será adicionada", example = "1", required = true)
            @PathVariable Integer comentarioForumId,
            @Parameter(description = "DTO contendo os dados da resposta", required = true)
            @RequestBody @Valid RespostaForumRequestDTO dto) {

        RespostaForumResponseDTO respostaDTO = respostaForumService.criarRespostaForum(comentarioForumId, dto);
        return new ServiceResponse<>(respostaDTO, "Resposta criada com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Respostas por Comentário",
            description = "Retorna uma lista paginada de respostas associadas a um comentário específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Respostas encontradas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhuma resposta encontrada para o comentário")
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<RespostaForumResponseDTO>> listarRespostasPorComentario(
            @Parameter(description = "ID do comentário para listar as respostas", example = "1", required = true)
            @PathVariable Integer comentarioForumId,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Page<RespostaForumResponseDTO> respostasPage = respostaForumService.listarPorComentario(comentarioForumId, page, size);
        String mensagem = respostasPage.isEmpty() ? "Nenhuma resposta encontrada" : "Respostas encontradas";
        return new ServiceResponse<>(respostasPage, mensagem, !respostasPage.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar Resposta",
            description = "Deleta uma resposta específica pelo seu ID, desde que pertença ao comentário informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resposta deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRespostaForum(
            @Parameter(description = "ID do comentário ao qual a resposta pertence", example = "1", required = true)
            @PathVariable Integer comentarioForumId,
            @Parameter(description = "ID da resposta a ser deletada", example = "1", required = true)
            @PathVariable Integer id) {
        respostaForumService.deletarRespostaForum(comentarioForumId, id);
    }

    @Operation(
            summary = "Buscar Resposta por ID",
            description = "Busca uma resposta específica pelo seu ID, desde que pertença ao comentário informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resposta encontrada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaForumResponseDTO> buscarRespostaPorId(
            @Parameter(description = "ID do comentário ao qual a resposta pertence", example = "1", required = true)
            @PathVariable Integer comentarioForumId,
            @Parameter(description = "ID da resposta a ser buscada", example = "1", required = true)
            @PathVariable Integer id) {

        RespostaForumResponseDTO respostaDTO = respostaForumService.buscarRespostaForum(comentarioForumId, id);
        return new ServiceResponse<>(respostaDTO, "Resposta encontrada", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}