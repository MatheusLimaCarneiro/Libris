package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.ComentarioForum;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.RespostaForum;
import prati.projeto.redeSocial.repository.ComentarioForumRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.RespostaForumRepository;
import prati.projeto.redeSocial.rest.dto.RespostaForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.service.NotificacaoService;
import prati.projeto.redeSocial.service.RespostaForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RespostaForumServiceImpl implements RespostaForumService {

    private final RespostaForumRepository respostaForumRepository;
    private final ComentarioForumRepository comentarioForumRepository;
    private final PerfilRepository perfilRepository;
    private final NotificacaoService notificacaoService;

    @Override
    public RespostaForumResponseDTO criarRespostaForum(Integer comentarioForumId, RespostaForumRequestDTO dto) {
        ComentarioForum comentarioForum = comentarioForumRepository.findById(comentarioForumId)
                .orElseThrow(() -> new RegraNegocioException("Comentario Forum não encontrado!"));
        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado!"));

        RespostaForum respostaForum = new RespostaForum();
        respostaForum.setComentarioForum(comentarioForum);
        respostaForum.setPerfil(perfil);
        respostaForum.setTexto(dto.getTexto());
        respostaForum.setData(LocalDateTime.now());

        respostaForumRepository.save(respostaForum);

        Perfil autorComentario = comentarioForum.getPerfil();
        notificacaoService.criarNotificacao(
                autorComentario,
                perfil,
                perfil.getUsuario().getUsername() + " respondeu ao seu comentário no fórum.",
                "resposta_forum"
        );

        return converterParaDTO(respostaForum);
    }

    @Override
    public Page<RespostaForumResponseDTO> listarPorComentario(Integer cometarioForumId, int page, int size) {
        if (!comentarioForumRepository.existsById(cometarioForumId)){
            throw new RegraNegocioException("Comentario Forum não encontrado com ID: " + cometarioForumId);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<RespostaForum> respostaForumPage = respostaForumRepository
                .findByComentarioForumId(cometarioForumId, pageable);

        return respostaForumPage.map(this::converterParaDTO);
    }

    @Override
    public void deletarRespostaForum(Integer cometarioForumId, Integer id) {
        RespostaForum respostaForum = respostaForumRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Resposta Forum não encontrado!"));

        if (!respostaForum.getComentarioForum().getId().equals(cometarioForumId)) {
            throw new RegraNegocioException("A Resposta não pertence ao Comentario informado!");
        }

        respostaForumRepository.deleteById(id);
    }

    @Override
    public RespostaForumResponseDTO buscarRespostaForum(Integer comentarioForumId, Integer respostaId) {
        RespostaForum respostaForum = respostaForumRepository.findById(respostaId)
                .orElseThrow(() -> new RegraNegocioException("Resposta não encontrada!"));

        if (!respostaForum.getComentarioForum().getId().equals(comentarioForumId)) {
            throw new RegraNegocioException("A Resposta não pertence ao Comentario informado!");
        }

        return converterParaDTO(respostaForum);
    }

    private RespostaForumResponseDTO converterParaDTO(RespostaForum respostaForum) {
        RespostaForumResponseDTO dto = new RespostaForumResponseDTO();
        dto.setId(respostaForum.getId());
        dto.setTexto(respostaForum.getTexto());
        dto.setNomePerfil(respostaForum.getPerfil().getUsuario().getUsername());
        dto.setQuantidadeCurtidas(respostaForum.getQuantidadeCurtidas());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = respostaForum.getData().format(formatter);
        dto.setData(dataFormatada);
        return dto;
    }
}
