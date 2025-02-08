package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Comentario;
import prati.projeto.redeSocial.modal.entity.ComentarioResposta;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.repository.ComentarioRepository;
import prati.projeto.redeSocial.repository.ComentarioRespostaRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComentarioRespostaServiceImpl implements ComentarioRespostaService {

    private final ComentarioRespostaRepository respostaRepository;
    private final ComentarioRepository comentarioRepository;
    private final PerfilRepository perfilRepository;

    @Override
    @Transactional
    public RespostaDTO adicionarResposta(Integer comentarioId, RespostaDTO respostaDTO) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        Perfil perfil = perfilRepository.findById(respostaDTO.getPerfilId())
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));

        ComentarioResposta resposta = new ComentarioResposta();
        resposta.setComentarioOriginal(comentario);
        resposta.setPerfil(perfil);
        resposta.setTexto(respostaDTO.getTexto());
        resposta.setDataResposta(LocalDateTime.now());

        resposta = respostaRepository.save(resposta);
        return convertToDTO(resposta);
    }

    @Override
    public Page<RespostaDTO> listarRespostasPorComentario(Integer comentarioId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ComentarioResposta> respostasPage = respostaRepository.findByComentarioOriginalId(comentarioId, pageable);
        return respostasPage.map(this::convertToDTO);
    }

    @Override
    @Transactional
    public RespostaDTO atualizarResposta(Integer comentarioId, Integer respostaId, RespostaDTO respostaDTO) {
        ComentarioResposta resposta = respostaRepository.findById(respostaId)
                .orElseThrow(() -> new RegraNegocioException("Resposta não encontrada"));

        if (!resposta.getComentarioOriginal().getId().equals(comentarioId)) {
            throw new RegraNegocioException("A resposta não pertence ao comentário informado.");
        }

        resposta.setTexto(respostaDTO.getTexto());
        resposta.setDataResposta(LocalDateTime.now());

        resposta = respostaRepository.save(resposta);
        return convertToDTO(resposta);
    }

    @Override
    @Transactional
    public void deletarResposta(Integer comentarioId, Integer respostaId) {
        ComentarioResposta resposta = respostaRepository.findById(respostaId)
                .orElseThrow(() -> new RegraNegocioException("Resposta não encontrada"));

        if (!resposta.getComentarioOriginal().getId().equals(comentarioId)) {
            throw new RegraNegocioException("A resposta não pertence ao comentário informado.");
        }

        respostaRepository.deleteById(respostaId);
    }

    @Override
    public RespostaDTO buscarRespostaPorId(Integer comentarioId, Integer respostaId) {
        ComentarioResposta resposta = respostaRepository.findById(respostaId)
                .orElseThrow(() -> new RegraNegocioException("Resposta não encontrada"));

        if (!resposta.getComentarioOriginal().getId().equals(comentarioId)) {
            throw new RegraNegocioException("A resposta não pertence ao comentário informado.");
        }

        return convertToDTO(resposta);
    }

    private RespostaDTO convertToDTO(ComentarioResposta resposta) {
        return new RespostaDTO(
                resposta.getId(),
                resposta.getPerfil().getId(),
                resposta.getTexto(),
                resposta.getDataResposta()
        );
    }
}
