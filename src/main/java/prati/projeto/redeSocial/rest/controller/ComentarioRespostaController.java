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
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios/{comentarioId}/respostas")
@Tag(
        name = "Respostas de Comentários",
        description = "Endpoints para gerenciar respostas a comentários. " +
                "Permite adicionar, buscar, listar, atualizar e deletar respostas associadas a um comentário específico."
)
public class ComentarioRespostaController {

    @Autowired
    private ComentarioRespostaService respostaService;

    @Operation(
            summary = "Adicionar Resposta",
            description = "Adiciona uma nova resposta a um comentário específico. " +
                    "A resposta é associada ao comentário e ao perfil que a criou.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resposta adicionada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos"),
                    @ApiResponse(responseCode = "404", description = "Comentário ou perfil não encontrado")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<RespostaDTO> adicionarResposta(
            @Parameter(description = "ID do comentário ao qual a resposta será adicionada", example = "1", required = true)
            @PathVariable Integer comentarioId,
            @Parameter(description = "DTO contendo os dados da resposta", required = true)
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO resposta = respostaService.adicionarResposta(comentarioId, respostaDTO);
        return new ServiceResponse<>(resposta, "Resposta adicionada com sucesso", true, getFormattedTimestamp());
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
    @GetMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaDTO> buscarRespostaPorId(
            @Parameter(description = "ID do comentário ao qual a resposta pertence", example = "1", required = true)
            @PathVariable Integer comentarioId,
            @Parameter(description = "ID da resposta a ser buscada", example = "1", required = true)
            @PathVariable Integer respostaId) {
        RespostaDTO resposta = respostaService.buscarRespostaPorId(comentarioId, respostaId);
        return new ServiceResponse<>(resposta, "Resposta encontrada com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Respostas",
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
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<RespostaDTO>> listarRespostas(
            @Parameter(description = "ID do comentário para listar as respostas", example = "1", required = true)
            @PathVariable Integer comentarioId,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Page<RespostaDTO> respostas = respostaService.listarRespostasPorComentario(comentarioId, page, size);
        String mensagem = respostas.isEmpty() ? "Nenhuma resposta encontrada" : "Respostas encontradas";
        return new ServiceResponse<>(respostas, mensagem, !respostas.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Atualizar Resposta",
            description = "Atualiza o texto de uma resposta específica, desde que pertença ao comentário informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resposta atualizada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado")
            }
    )
    @PutMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaDTO> atualizarResposta(
            @Parameter(description = "ID do comentário ao qual a resposta pertence", example = "1", required = true)
            @PathVariable Integer comentarioId,
            @Parameter(description = "ID da resposta a ser atualizada", example = "1", required = true)
            @PathVariable Integer respostaId,
            @Parameter(description = "DTO contendo o novo texto da resposta", required = true)
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO respostaAtualizada = respostaService.atualizarResposta(comentarioId, respostaId, respostaDTO);
        return new ServiceResponse<>(respostaAtualizada, "Resposta atualizada com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar Resposta",
            description = "Deleta uma resposta específica, desde que pertença ao comentário informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resposta deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado")
            }
    )
    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarResposta(
            @Parameter(description = "ID do comentário ao qual a resposta pertence", example = "1", required = true)
            @PathVariable Integer comentarioId,
            @Parameter(description = "ID da resposta a ser deletada", example = "1", required = true)
            @PathVariable Integer respostaId) {
        respostaService.deletarResposta(comentarioId, respostaId);
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}