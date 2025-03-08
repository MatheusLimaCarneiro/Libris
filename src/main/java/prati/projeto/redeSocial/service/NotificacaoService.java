package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.NotificacaoDTO;

public interface NotificacaoService {

    NotificacaoDTO criarNotificacao(Perfil destinatario, Perfil remetente, String mensagem, String tipo);

    Page<NotificacaoDTO> buscarNotificacoesPorPerfil(Integer perfilId, int page, int size);

    Page<NotificacaoDTO> buscarNotificacoesPorUsername(String username, int page, int size);

    void deletarNotificacao(Integer id, Integer usuarioId);

    void marcarComoLida(Integer id, Integer perfilId);

    void marcarTodasComoLida(Integer perfilId);

    void deletarTodasNotificacoes(Integer perfilId);

    void deletarTodasNotificacoesPorUsername(String username);
}
