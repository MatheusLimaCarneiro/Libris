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
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.AvaliacaoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/resenhas/{resenhaId}/avaliacoes")
@Tag(
        name = "Avaliação",
        description = "Endpoints responsáveis pela avaliação da resenha."
)
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;


    @Operation(
            summary = "Registrar uma avaliação",
            description = "Registra uma nova avaliação e retorna as informações da avaliação cadastrada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Avaliação adicionada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição.")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<AvaliacaoDTO> adicionarAvaliacao(
            @PathVariable Integer resenhaId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.adicionarAvaliacao(resenhaId, avaliacaoDTO);
        return new ServiceResponse<>(resultado, "Avaliação adicionada com sucesso.", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Listar avaliações por resenha",
            description = "Lista todas as avaliações de uma resenha, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliações listadas com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada.")
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorResenha(
            @PathVariable Integer resenhaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> avaliacoes = avaliacaoService.listarAvaliacaoPorResenha(resenhaId, page, size);
        String mensagem = avaliacoes.isEmpty() ? "Nenhuma avaliação encontrada" : "Avaliações encontradas com sucesso";
        return new ServiceResponse<>(avaliacoes, mensagem, !avaliacoes.isEmpty(), getFormattedTimestamp());
    }



    @Operation(
            summary = "Atualizar avaliação",
            description = "Atualiza uma avaliação existente e retorna as informações atualizadas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliação atualizada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
                    @ApiResponse(responseCode = "404", description = "Avaliação ou resenha não encontrada.")
            }
    )
    @PutMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<AvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Integer resenhaId,
            @PathVariable Integer avaliacaoId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.editarAvaliacao(resenhaId, avaliacaoId, avaliacaoDTO);
        return new ServiceResponse<>(resultado, "Avaliação atualizada com sucesso.", true, getFormattedTimestamp());
    }




    @Operation(
            summary = "Deletar avaliação",
            description = "Remove uma avaliação existente associada à resenha informada.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Avaliação ou resenha não encontrada.")
            }
    )
    @DeleteMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAvaliacao(
            @PathVariable Integer resenhaId,
            @PathVariable Integer avaliacaoId) {
        avaliacaoService.deletarAvaliacao(resenhaId, avaliacaoId);
    }




    @Operation(
            summary = "Listar avaliações por perfil",
            description = "Lista as avaliações de uma resenha filtradas por perfil do usuário, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliações do perfil listadas com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil ou resenha não encontrada.")
            }
    )
    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorPerfil(
            @PathVariable Integer resenhaId,
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> resultado = avaliacaoService.listarAvaliacoesPorPerfil(perfilId, page, size);
        String mensagem = resultado.isEmpty() ? "Nenhuma avaliação encontrada para este perfil." : "Avaliações do perfil listadas com sucesso.";
        return new ServiceResponse<>(resultado.isEmpty() ? null : resultado, mensagem, !resultado.isEmpty(), getFormattedTimestamp());
    }


    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}