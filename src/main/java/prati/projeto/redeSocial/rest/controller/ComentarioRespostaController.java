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
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios/{comentarioId}/respostas")
@Tag(
        name = "Resposta de Comentário",
        description = "Endpoints responsáveis pela gestão das respostas aos comentários."
)
public class ComentarioRespostaController {

    @Autowired
    private ComentarioRespostaService respostaService;


    @Operation(
            summary = "Adicionar resposta",
            description = "Adiciona uma nova resposta ao comentário especificado e retorna os detalhes da resposta criada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resposta adicionada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da resposta.")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<RespostaDTO> adicionarResposta(
            @PathVariable Integer comentarioId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO resposta = respostaService.adicionarResposta(comentarioId, respostaDTO);
        return new ServiceResponse<>(resposta, "Resposta adicionada com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Buscar resposta por ID",
            description = "Retorna os detalhes da resposta associada ao comentário com base nos identificadores fornecidos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resposta encontrada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado.")
            }
    )
    @GetMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaDTO> buscarRespostaPorId(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId) {
        RespostaDTO resposta = respostaService.buscarRespostaPorId(comentarioId, respostaId);
        return new ServiceResponse<>(resposta, "Resposta encontrada com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Listar respostas",
            description = "Lista todas as respostas associadas ao comentário especificado, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Respostas encontradas.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<RespostaDTO>> listarRespostas(
            @PathVariable Integer comentarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RespostaDTO> respostas = respostaService.listarRespostasPorComentario(comentarioId, page, size);
        String mensagem = respostas.isEmpty() ? "Nenhuma resposta encontrada" : "Respostas encontradas";
        return new ServiceResponse<>(respostas, mensagem, !respostas.isEmpty(), getFormattedTimestamp());
    }



    @Operation(
            summary = "Atualizar resposta",
            description = "Atualiza os dados de uma resposta existente associada ao comentário e retorna os detalhes atualizados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resposta atualizada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado.")
            }
    )
    @PutMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaDTO> atualizarResposta(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO respostaAtualizada = respostaService.atualizarResposta(comentarioId, respostaId, respostaDTO);
        return new ServiceResponse<>(respostaAtualizada, "Resposta atualizada com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Deletar resposta",
            description = "Exclui a resposta associada ao comentário com base nos identificadores fornecidos.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resposta excluída com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Resposta ou comentário não encontrado.")
            }
    )
    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarResposta(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId) {
        respostaService.deletarResposta(comentarioId, respostaId);
    }



    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}