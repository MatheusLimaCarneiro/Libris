package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Notificacao;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.repository.NotificacaoRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.rest.dto.NotificacaoDTO;
import prati.projeto.redeSocial.service.NotificacaoService;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NotificacaoServiceImpl implements NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final PerfilRepository perfilRepository;

    @Override
    @Transactional
    public NotificacaoDTO criarNotificacao(Perfil destinatario, Perfil remetente, String mensagem, String tipo) {
        if (!perfilRepository.existsById(remetente.getId())) {
            throw new RegraNegocioException("Remetente não encontrado.");
        }
        if (!perfilRepository.existsById(destinatario.getId())) {
            throw new RegraNegocioException("Destinatario não encontrado.");
        }

        Notificacao notificacao = new Notificacao();
        notificacao.setDestinatario(destinatario);
        notificacao.setRemetente(remetente);
        notificacao.setMensagem(mensagem);
        notificacao.setTipo(tipo);
        notificacaoRepository.save(notificacao);

        return converterParaDTO(notificacao);
    }

    @Override
    public Page<NotificacaoDTO> buscarNotificacoesPorPerfil(Integer perfilId, int page, int size)  {
        verificarExistenciaPerfil(perfilId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Notificacao> notificacoesPage = notificacaoRepository.findByDestinatarioId(perfilId, pageable);

        return notificacoesPage.map(this::converterParaDTO);
    }

    @Override
    public Page<NotificacaoDTO> buscarNotificacoesPorUsername(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notificacao> notificacoesPage = notificacaoRepository.findByDestinatarioUsername(username, pageable);
        return notificacoesPage.map(this::converterParaDTO);    }

    @Override
    @Transactional
    public void deletarNotificacao(Integer id, Integer perfilId) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Notificação não encontrada."));

        verificarExistenciaPerfil(perfilId);

        if (!notificacao.getDestinatario().getId().equals(perfilId)) {
            throw new RegraNegocioException("Você não tem permissão para deletar esta notificação.");
        }

        notificacaoRepository.delete(notificacao);
    }

    @Override
    @Transactional
    public void marcarComoLida(Integer id, Integer perfilId) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Notificação não encontrada."));

        verificarExistenciaPerfil(perfilId);

        if (!notificacao.getDestinatario().getId().equals(perfilId)) {
            throw new RegraNegocioException("Você não tem permissão para marcar esta notificação como lida.");
        }

        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }

    @Override
    @Transactional
    public void marcarTodasComoLida(Integer perfilId) {
        verificarExistenciaPerfil(perfilId);

        notificacaoRepository.marcarTodasComoLida(perfilId);
    }

    @Override
    @Transactional
    public void deletarTodasNotificacoes(Integer perfilId) {
        verificarExistenciaPerfil(perfilId);

        notificacaoRepository.deletarTodasPorDestinatarioId(perfilId);
    }

    private void verificarExistenciaPerfil(Integer perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new RegraNegocioException("Perfil não encontrado.");
        }
    }

    private NotificacaoDTO converterParaDTO(Notificacao notificacao) {
        NotificacaoDTO dto = new NotificacaoDTO();
        dto.setId(notificacao.getId());
        dto.setMensagem(notificacao.getMensagem());
        dto.setTipo(notificacao.getTipo());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataCriacaoFormatada = notificacao.getDataCriacao().format(formatter);
        dto.setDataCriacao(dataCriacaoFormatada);

        dto.setRemetenteId(notificacao.getRemetente().getId());
        dto.setRemetenteUsername(notificacao.getRemetente().getUsuario().getUsername());
        dto.setLida(notificacao.isLida());
        return dto;
    }
}
