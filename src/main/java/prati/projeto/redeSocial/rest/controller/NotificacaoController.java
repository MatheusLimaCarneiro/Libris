package prati.projeto.redeSocial.rest.controller;

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
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<NotificacaoDTO>> buscarNotificacoesPorPerfil(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.buscarNotificacoesPorPerfil(perfilId, page, size);
        String mensagem = notificacoes.isEmpty() ? "Nenhuma notificação encontrada" : "Notificações encontradas";
        return new ServiceResponse<>(notificacoes, mensagem, !notificacoes.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<NotificacaoDTO>> buscarNotificacoesPorUsername(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<NotificacaoDTO> notificacoes = notificacaoService.buscarNotificacoesPorUsername(username, page, size);
        String mensagem = notificacoes.isEmpty() ? "Nenhuma notificação encontrada" : "Notificações encontradas";
        return new ServiceResponse<>(notificacoes, mensagem, !notificacoes.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> deletarNotificacao( @PathVariable Integer id, @RequestParam Integer perfilId) {
        notificacaoService.deletarNotificacao(id, perfilId);
        return new ServiceResponse<>(null, "Notificação deletada com sucesso", true, getFormattedTimestamp());
    }

    @PatchMapping("/{id}/marcar-como-lida")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> marcarComoLida(
            @PathVariable Integer id,
            @RequestParam Integer perfilId
    ) {
        notificacaoService.marcarComoLida(id, perfilId);
        return new ServiceResponse<>(null, "Notificação marcada como lida", true, getFormattedTimestamp());
    }

    @PatchMapping("/marcar-todas-como-lida")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> marcarTodasComoLida(@RequestParam Integer perfilId) {
        notificacaoService.marcarTodasComoLida(perfilId);
        return new ServiceResponse<>(null, "Todas as notificações foram marcadas como lidas", true, getFormattedTimestamp());
    }

    @DeleteMapping("/deletar-todas")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> deletarTodasNotificacoes(@RequestParam Integer perfilId) {
        notificacaoService.deletarTodasNotificacoes(perfilId);
        return new ServiceResponse<>(null, "Todas as notificações foram deletadas", true, getFormattedTimestamp());
    }

    @DeleteMapping("/deletar-todas/username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> deletarTodasNotificacoesPorUsername(@PathVariable String username) {
        notificacaoService.deletarTodasNotificacoesPorUsername(username);
        return new ServiceResponse<>(null, "Todas as notificações do usuário " + username + " foram deletadas", true, getFormattedTimestamp()
        );
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}