package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;

public interface PostForumService {

    PostForumResponseDTO criarPost(PostForumRequestDTO dto);
    Page<PostForumResponseDTO> listarTodos(int page, int size);
    PostForumResponseDTO buscarPorId(Integer id);
    void deletarPost(Integer id);
    Page<PostForumResponseDTO> filtrarPosts(String tags, String username, String livroNome, int page, int size);
}
