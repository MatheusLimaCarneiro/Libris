package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.ComentarioForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;

public interface ComentarioForumService {

    /**
     * Cria um novo comentário em um post de fórum.
     *
     * @param postId O ID do post onde o comentário será criado.
     * @param dto O DTO contendo os dados do comentário a ser criado.
     * @return Um {@link ComentarioForumResponseDTO} representando o comentário criado.
     */
    ComentarioForumResponseDTO criarComentario(Integer postId, ComentarioForumRequestDTO dto);

    /**
     * Lista os comentários de um post de forma paginada.
     *
     * @param postId O ID do post cujos comentários serão listados.
     * @param page O número da página a ser retornada.
     * @param size O número de comentários por página.
     * @return Uma página de {@link ComentarioForumResponseDTO} contendo os comentários paginados.
     */
    Page<ComentarioForumResponseDTO> listarPorPost(Integer postId, int page, int size);

    /**
     * Deleta um comentário de um post.
     *
     * @param postId O ID do post ao qual o comentário pertence.
     * @param id O ID do comentário a ser deletado.
     */
    void deletarComentario(Integer postId, Integer id);

    /**
     * Busca um comentário específico em um post.
     *
     * @param postId O ID do post ao qual o comentário pertence.
     * @param comentarioId O ID do comentário a ser buscado.
     * @return Um {@link ComentarioForumResponseDTO} representando o comentário encontrado.
     */
    ComentarioForumResponseDTO buscarComentario(Integer postId, Integer comentarioId);

    /**
     * Lista os comentários de um usuário específico em um post de forma paginada.
     *
     * @param username O username do usuário cujos comentários serão listados.
     * @param postId O ID do post ao qual os comentários pertencem.
     * @param page O número da página a ser retornada.
     * @param size O número de comentários por página.
     * @return Uma página de {@link ComentarioForumResponseDTO} contendo os comentários paginados.
     */
    Page<ComentarioForumResponseDTO> listarComentariosPorUsername(String username, Integer postId, int page, int size);
}