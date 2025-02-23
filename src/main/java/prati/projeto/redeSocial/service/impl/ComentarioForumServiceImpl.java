package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.ComentarioForum;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.PostForum;
import prati.projeto.redeSocial.modal.entity.RespostaForum;
import prati.projeto.redeSocial.repository.ComentarioForumRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.PostForumRepository;
import prati.projeto.redeSocial.repository.RespostaForumRepository;
import prati.projeto.redeSocial.rest.dto.ComentarioForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.service.ComentarioForumService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioForumServiceImpl implements ComentarioForumService {

    private final ComentarioForumRepository comentarioForumRepository;
    private final PostForumRepository postForumRepository;
    private final PerfilRepository perfilRepository;
    private final RespostaForumRepository respostaForumRepository;

    @Override
    @Transactional
    public ComentarioForumResponseDTO criarComentario(Integer postId, ComentarioForumRequestDTO dto) {
        PostForum post = postForumRepository.findById(postId)
                .orElseThrow(() -> new RegraNegocioException("Post não encontrado!"));

        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado!"));

        ComentarioForum comentario = new ComentarioForum();
        comentario.setTexto(dto.getTexto());
        comentario.setPerfil(perfil);
        comentario.setPostForum(post);
        comentario.setData(LocalDateTime.now());
        comentario.setRespostas(new ArrayList<>());

        comentarioForumRepository.save(comentario);

        return converterParaDTO(comentario);
    }

    @Override
    public Page<ComentarioForumResponseDTO> listarPorPost(Integer postId, int page, int size) {
        if (!postForumRepository.existsById(postId)) {
            throw new RegraNegocioException("Post não encontrado com ID: " + postId);
        }

        Pageable comentariosPageable = PageRequest.of(page, size);
        Page<ComentarioForum> comentariosPage = comentarioForumRepository.findByPostForumId(postId, comentariosPageable);

        return comentariosPage.map(comentario -> {
            ComentarioForumResponseDTO dto = converterParaDTO(comentario);

            Pageable respostasPageable = PageRequest.of(0, 10);
            Page<RespostaForum> respostasPage = respostaForumRepository.findByComentarioForumId(comentario.getId(), respostasPageable);

            List<RespostaForumResponseDTO> respostasDTO = respostasPage.getContent().stream()
                    .map(this::converterRespostaParaDTO)
                    .collect(Collectors.toList());
            dto.setRespostas(respostasDTO);

            return dto;
        });
    }

    @Override
    @Transactional
    public void deletarComentario(Integer postId, Integer id) {
        ComentarioForum comentario = comentarioForumRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado para exclusão!"));

        if (!comentario.getPostForum().getId().equals(postId)) {
            throw new RegraNegocioException("O comentário não pertence ao post informado!");
        }

        comentarioForumRepository.deleteById(id);
    }

    @Override
    public ComentarioForumResponseDTO buscarComentario(Integer postId, Integer comentarioId) {
        ComentarioForum comentario = comentarioForumRepository.findById(comentarioId)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado!"));

        if (!comentario.getPostForum().getId().equals(postId)) {
            throw new RegraNegocioException("O comentário não pertence ao post informado!");
        }

        Pageable respostasPageable = PageRequest.of(0, 10);
        Page<RespostaForum> respostasPage = respostaForumRepository.findByComentarioForumId(comentario.getId(), respostasPageable);
        List<RespostaForumResponseDTO> respostasDTO = respostasPage.getContent().stream()
                .map(this::converterRespostaParaDTO)
                .collect(Collectors.toList());

        ComentarioForumResponseDTO dto = converterParaDTO(comentario);
        dto.setRespostas(respostasDTO);

        return dto;
    }

    private ComentarioForumResponseDTO converterParaDTO(ComentarioForum comentario) {
        ComentarioForumResponseDTO dto = new ComentarioForumResponseDTO();
        dto.setId(comentario.getId());
        dto.setTexto(comentario.getTexto());
        dto.setNomePerfil(comentario.getPerfil().getUsuario().getUsername());
        dto.setData(comentario.getData());
        dto.setQuantidadeCurtidas(comentario.getQuantidadeCurtidas());

        List<RespostaForumResponseDTO> respostasDTO = comentario.getRespostas().stream()
                .map(this::converterRespostaParaDTO)
                .collect(Collectors.toList());
        dto.setRespostas(respostasDTO);

        return dto;
    }

    private RespostaForumResponseDTO converterRespostaParaDTO(RespostaForum resposta) {
        RespostaForumResponseDTO dto = new RespostaForumResponseDTO();
        dto.setId(resposta.getId());
        dto.setTexto(resposta.getTexto());
        dto.setNomePerfil(resposta.getPerfil().getUsuario().getUsername());
        dto.setData(resposta.getData());
        return dto;
    }
}