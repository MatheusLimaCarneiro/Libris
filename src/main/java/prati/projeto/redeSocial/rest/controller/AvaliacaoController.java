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
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.AvaliacaoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/resenhas/{resenhaId}/avaliacoes")
@Tag(
        name = "Avaliações",
        description = "Endpoints que gerenciam as avaliações de resenhas. " +
                "Permite adicionar, listar, atualizar e deletar avaliações, bem como listar avaliações por perfil. " +
                "Esses endpoints fornecem funcionalidades para que os usuários possam avaliar resenhas e visualizar avaliações."
)
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Operation(
            summary = "Adicionar avaliação",
            description = "Adiciona uma nova avaliação a uma resenha específica.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Avaliação adicionada com sucesso",
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
    public ServiceResponse<AvaliacaoDTO> adicionarAvaliacao(
            @Parameter(description = "ID da resenha", example = "1") @PathVariable Integer resenhaId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.adicionarAvaliacao(resenhaId, avaliacaoDTO);
        return new ServiceResponse<>(resultado, "Avaliação adicionada com sucesso.", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar avaliações por resenha",
            description = "Retorna uma lista paginada de avaliações associadas a uma resenha específica.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliações encontradas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhuma avaliação encontrada para a resenha")
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorResenha(
            @Parameter(description = "ID da resenha", example = "1") @PathVariable Integer resenhaId,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> avaliacoes = avaliacaoService.listarAvaliacaoPorResenha(resenhaId, page, size);
        String mensagem = avaliacoes.isEmpty() ? "Nenhuma avaliação encontrada" : "Avaliações encontradas com sucesso";
        return new ServiceResponse<>(avaliacoes, mensagem, !avaliacoes.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Atualizar avaliação",
            description = "Atualiza uma avaliação existente de uma resenha.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliação atualizada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
            }
    )
    @PutMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<AvaliacaoDTO> atualizarAvaliacao(
            @Parameter(description = "ID da resenha", example = "1") @PathVariable Integer resenhaId,
            @Parameter(description = "ID da avaliação", example = "1") @PathVariable Integer avaliacaoId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.editarAvaliacao(resenhaId, avaliacaoId, avaliacaoDTO);
        return new ServiceResponse<>(resultado, "Avaliação atualizada com sucesso.", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar avaliação",
            description = "Deleta uma avaliação específica de uma resenha.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Avaliação deletada com sucesso"
                    ),
                    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
            }
    )
    @DeleteMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAvaliacao(
            @Parameter(description = "ID da resenha", example = "1") @PathVariable Integer resenhaId,
            @Parameter(description = "ID da avaliação", example = "1") @PathVariable Integer avaliacaoId) {
        avaliacaoService.deletarAvaliacao(resenhaId, avaliacaoId);
    }

    @Operation(
            summary = "Listar avaliações por perfil",
            description = "Retorna uma lista paginada de avaliações feitas por um perfil específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Avaliações do perfil listadas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )

                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhuma avaliação encontrada para o perfil")
            }
    )
    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorPerfil(
            @Parameter(description = "ID da resenha", example = "1") @PathVariable Integer resenhaId,
            @Parameter(description = "ID do perfil", example = "1") @PathVariable Integer perfilId,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> resultado = avaliacaoService.listarAvaliacoesPorPerfil(perfilId, page, size);
        String mensagem = resultado.isEmpty() ? "Nenhuma avaliação encontrada para este perfil." : "Avaliações do perfil listadas com sucesso.";
        return new ServiceResponse<>(resultado.isEmpty() ? null : resultado, mensagem, !resultado.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}