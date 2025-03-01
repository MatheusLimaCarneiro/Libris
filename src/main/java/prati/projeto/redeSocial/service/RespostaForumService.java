package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.RespostaForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;

public interface RespostaForumService {
    RespostaForumResponseDTO criarRespostaForum(Integer comentarioForumId, RespostaForumRequestDTO dto);
    Page<RespostaForumResponseDTO>listarPorComentario(Integer cometarioForumId, int page, int size);
    void deletarRespostaForum(Integer cometarioForumId, Integer id);
    RespostaForumResponseDTO buscarRespostaForum(Integer comentarioForumId, Integer respostaId);
}
