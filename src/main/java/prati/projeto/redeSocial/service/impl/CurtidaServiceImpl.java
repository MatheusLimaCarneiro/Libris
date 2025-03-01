package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Comentario;
import prati.projeto.redeSocial.modal.entity.ComentarioForum;
import prati.projeto.redeSocial.modal.entity.ComentarioResposta;
import prati.projeto.redeSocial.modal.entity.RespostaForum;
import prati.projeto.redeSocial.repository.*;
import prati.projeto.redeSocial.service.CurtidaService;

@Service
@RequiredArgsConstructor
public class CurtidaServiceImpl implements CurtidaService {

    private final PerfilRepository perfilRepository;
    private final ComentarioRepository comentarioRepository;
    private final ComentarioRespostaRepository respostaRepository;
    private final ComentarioForumRepository comentarioForumRepository;
    private final RespostaForumRepository respostaForumRepository;

    @Override
    @Transactional
    public void curtirComentario(Integer perfilId, Integer comentarioId) {
        validarPerfil(perfilId);
        Comentario comentario = buscarComentario(comentarioId);

        if (comentario.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você já curtiu este comentário.");
        }

        comentario.getPerfisQueCurtiram().add(perfilId);
        comentario.setQuantidadeCurtidas(comentario.getQuantidadeCurtidas() + 1);
        comentarioRepository.save(comentario);
    }

    @Override
    @Transactional
    public void descurtirComentario(Integer perfilId, Integer comentarioId) {
        validarPerfil(perfilId);
        Comentario comentario = buscarComentario(comentarioId);

        if (!comentario.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você ainda não curtiu este comentário.");
        }

        comentario.getPerfisQueCurtiram().remove(perfilId);
        comentario.setQuantidadeCurtidas(comentario.getQuantidadeCurtidas() - 1);
        comentarioRepository.save(comentario);
    }

    @Override
    @Transactional
    public void curtirResposta(Integer perfilId, Integer respostaId) {
        validarPerfil(perfilId);
        ComentarioResposta resposta = buscarResposta(respostaId);

        if (resposta.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você já curtiu esta resposta.");
        }

        resposta.getPerfisQueCurtiram().add(perfilId);
        resposta.setQuantidadeCurtidas(resposta.getQuantidadeCurtidas() + 1);
        respostaRepository.save(resposta);
    }

    @Override
    @Transactional
    public void descurtirResposta(Integer perfilId, Integer respostaId) {
        validarPerfil(perfilId);
        ComentarioResposta resposta = buscarResposta(respostaId);

        if (!resposta.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você ainda não curtiu esta resposta.");
        }

        resposta.getPerfisQueCurtiram().remove(perfilId);
        resposta.setQuantidadeCurtidas(resposta.getQuantidadeCurtidas() - 1);
        respostaRepository.save(resposta);
    }

    @Override
    @Transactional
    public void curtirComentarioForum(Integer perfilId, Integer comentarioForumId) {
        validarPerfil(perfilId);
        ComentarioForum comentarioForum = buscarComentarioForum(comentarioForumId);

        if (comentarioForum.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você já curtiu este comentário.");
        }

        comentarioForum.getPerfisQueCurtiram().add(perfilId);
        comentarioForum.setQuantidadeCurtidas(comentarioForum.getQuantidadeCurtidas() + 1);
        comentarioForumRepository.save(comentarioForum);
    }

    @Override
    @Transactional
    public void descurtirComentarioForum(Integer perfilId, Integer comentarioForumId) {
        validarPerfil(perfilId);
        ComentarioForum comentarioForum = buscarComentarioForum(comentarioForumId);

        if (!comentarioForum.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você ainda não curtiu este comentário.");
        }

        comentarioForum.getPerfisQueCurtiram().remove(perfilId);
        comentarioForum.setQuantidadeCurtidas(comentarioForum.getQuantidadeCurtidas() - 1);
        comentarioForumRepository.save(comentarioForum);
    }

    @Override
    @Transactional
    public void curtirRespostaForum(Integer perfilId, Integer respostaForumId) {
        validarPerfil(perfilId);
        RespostaForum respostaForum = buscarRespostaForum(respostaForumId);

        if (respostaForum.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você já curtiu esta resposta.");
        }

        respostaForum.getPerfisQueCurtiram().add(perfilId);
        respostaForum.setQuantidadeCurtidas(respostaForum.getQuantidadeCurtidas() + 1);
        respostaForumRepository.save(respostaForum);
    }

    @Override
    @Transactional
    public void descurtirRespostaForum(Integer perfilId, Integer respostaForumId) {
        validarPerfil(perfilId);
        RespostaForum respostaForum = buscarRespostaForum(respostaForumId);

        if (!respostaForum.getPerfisQueCurtiram().contains(perfilId)) {
            throw new RegraNegocioException("Você ainda não curtiu esta resposta.");
        }

        respostaForum.getPerfisQueCurtiram().remove(perfilId);
        respostaForum.setQuantidadeCurtidas(respostaForum.getQuantidadeCurtidas() - 1);
        respostaForumRepository.save(respostaForum);
    }

    private void validarPerfil(Integer perfilId) {
        perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
    }

    private Comentario buscarComentario(Integer comentarioId) {
        return comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));
    }

    private ComentarioResposta buscarResposta(Integer respostaId) {
        return respostaRepository.findById(respostaId)
                .orElseThrow(() -> new RegraNegocioException("Resposta não encontrada"));
    }

    private ComentarioForum buscarComentarioForum(Integer comentarioForumId) {
        return comentarioForumRepository.findById(comentarioForumId)
                .orElseThrow(() -> new RegraNegocioException("Comentário do fórum não encontrado"));
    }

    private RespostaForum buscarRespostaForum(Integer respostaForumId) {
        return respostaForumRepository.findById(respostaForumId)
                .orElseThrow(() -> new RegraNegocioException("Resposta do fórum não encontrada"));
    }
}