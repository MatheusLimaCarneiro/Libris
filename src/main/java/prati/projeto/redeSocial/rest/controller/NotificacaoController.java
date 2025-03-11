package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.NotificacaoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.NotificacaoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/notificacoes")
@Tag(name = "Notificações", description = "Endpoints para gerenciamento de notificações")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar notificações por perfil",
            description = "Retorna uma lista paginada de notificações associadas a um perfil específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notificações encontradas",
                            content = @Content(schema = @Schema(implementation = ServiceResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma notificação encontrada")
            })
    public ServiceResponse<Page<NotificacaoDTO>> buscarNotificacoesPorPerfil(
            @Parameter(description = "ID do perfil", required = true) @PathVariable Integer perfilId,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.buscarNotificacoesPorPerfil(perfilId, page, size);
        String mensagem = notificacoes.isEmpty() ? "Nenhuma notificação encontrada" : "Notificações encontradas";
        return new ServiceResponse<>(notificacoes, mensagem, !notificacoes.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar notificações por username",
            description = "Retorna uma lista paginada de notificações associadas a um username específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notificações encontradas",
                            content = @Content(schema = @Schema(implementation = ServiceResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma notificação encontrada")
            })
    public ServiceResponse<Page<NotificacaoDTO>> buscarNotificacoesPorUsername(
            @Parameter(description = "Username do usuário", required = true) @PathVariable String username,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.buscarNotificacoesPorUsername(username, page, size);
        String mensagem = notificacoes.isEmpty() ? "Nenhuma notificação encontrada" : "Notificações encontradas";
        return new ServiceResponse<>(notificacoes, mensagem, !notificacoes.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar notificação",
            description = "Deleta uma notificação específica com base no ID da notificação e no ID do perfil.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Notificação deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
            })
    public ServiceResponse<Void> deletarNotificacao(
            @Parameter(description = "ID da notificação", required = true) @PathVariable Integer id,
            @Parameter(description = "ID do perfil", required = true) @RequestParam Integer perfilId) {
        notificacaoService.deletarNotificacao(id, perfilId);
        return new ServiceResponse<>(null, "Notificação deletada com sucesso", true, getFormattedTimestamp());
    }

    @PatchMapping("/{id}/marcar-como-lida")
    @ResponseStatus(HttpStatus.OK)
    @Operation( summary = "Marcar notificação como lida por perfil",
            description = "Marca uma notificação específica como lida com base no ID da notificação e no ID do perfil associado. " +
                    "A notificação só será marcada como lida se pertencer ao perfil especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notificação marcada como lida"),
                    @ApiResponse(responseCode = "404", description = "Notificação não encontrada")
            })
    public ServiceResponse<Void> marcarComoLida(
            @Parameter(description = "ID da notificação", required = true) @PathVariable Integer id,
            @Parameter(description = "ID do perfil", required = true) @RequestParam Integer perfilId
    ) {
        notificacaoService.marcarComoLida(id, perfilId);
        return new ServiceResponse<>(null, "Notificação marcada como lida", true, getFormattedTimestamp());
    }

    @PatchMapping("/marcar-todas-como-lida")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Marcar todas as notificações como lidas",
            description = "Marca todas as notificações de um perfil específico como lidas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Todas as notificações foram marcadas como lidas"),
                    @ApiResponse(responseCode = "404", description = "Nenhuma notificação encontrada")
            })
    public ServiceResponse<Void> marcarTodasComoLida(
            @Parameter(description = "ID do perfil", required = true) @RequestParam Integer perfilId) {
        notificacaoService.marcarTodasComoLida(perfilId);
        return new ServiceResponse<>(null, "Todas as notificações foram marcadas como lidas", true, getFormattedTimestamp());
    }

    @DeleteMapping("/deletar-todas")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar todas as notificações",
            description = "Deleta todas as notificações de um perfil específico.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Todas as notificações foram deletadas"),
                    @ApiResponse(responseCode = "404", description = "Nenhuma notificação encontrada")
            })
    public ServiceResponse<Void> deletarTodasNotificacoes(
            @Parameter(description = "ID do perfil", required = true) @RequestParam Integer perfilId) {
        notificacaoService.deletarTodasNotificacoes(perfilId);
        return new ServiceResponse<>(null, "Todas as notificações foram deletadas", true, getFormattedTimestamp());
    }

    @DeleteMapping("/deletar-todas/username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar todas as notificações por username",
            description = "Deleta todas as notificações de um usuário específico com base no username.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Todas as notificações do usuário foram deletadas"),
                    @ApiResponse(responseCode = "404", description = "Nenhuma notificação encontrada")
            })
    public ServiceResponse<Void> deletarTodasNotificacoesPorUsername(
            @Parameter(description = "Username do usuário", required = true) @PathVariable String username) {
        notificacaoService.deletarTodasNotificacoesPorUsername(username);
        return new ServiceResponse<>(null, "Todas as notificações do usuário " + username + " foram deletadas", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}