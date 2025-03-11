package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.ComentarioForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;

public interface ComentarioForumService {
    ComentarioForumResponseDTO criarComentario(Integer postId, ComentarioForumRequestDTO dto);
    Page<ComentarioForumResponseDTO> listarPorPost(Integer postId, int page, int size);
    void deletarComentario(Integer postId, Integer id);
    ComentarioForumResponseDTO buscarComentario(Integer postId, Integer comentarioId);
    Page<ComentarioForumResponseDTO> listarComentariosPorUsername(String username, Integer postId, int page, int size);
}