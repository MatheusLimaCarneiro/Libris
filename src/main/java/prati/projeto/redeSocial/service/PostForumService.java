package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;
import prati.projeto.redeSocial.exception.RegraNegocioException;

public interface PostForumService {

    /**
     * Cria um novo post no fórum.
     * <p>
     * Este método recebe os dados do post em formato DTO, valida as informações,
     * associa o post ao perfil e ao livro correspondente, e persiste no banco de dados.
     * </p>
     *
     * @param dto Objeto DTO contendo os dados do post, como texto, tags, ID do perfil e Google ID do livro.
     * @return PostForumResponseDTO contendo os dados do post criado.
     * @throws RegraNegocioException Se o perfil ou livro não forem encontrados.
     */
    PostForumResponseDTO criarPost(PostForumRequestDTO dto);

    /**
     * Lista todos os posts do fórum.
     * <p>
     * Este método retorna uma lista paginada de todos os posts presentes no fórum,
     * incluindo informações sobre o perfil, livro e comentários associados.
     * </p>
     *
     * @param page Número da página a ser retornada.
     * @param size Quantidade de posts por página.
     * @return Página de PostForumResponseDTO contendo os posts do fórum.
     */
    Page<PostForumResponseDTO> listarTodos(int page, int size);

    /**
     * Busca um post específico pelo seu ID.
     * <p>
     * Este método retorna os detalhes de um post identificado pelo ID fornecido,
     * incluindo informações sobre o perfil, livro e comentários associados.
     * </p>
     *
     * @param id ID do post a ser buscado.
     * @return PostForumResponseDTO contendo os dados do post.
     * @throws RegraNegocioException Se o post com o ID fornecido não for encontrado.
     */
    PostForumResponseDTO buscarPorId(Integer id);

    /**
     * Deleta um post do fórum.
     * <p>
     * Este método remove permanentemente um post identificado pelo ID fornecido.
     * </p>
     *
     * @param id ID do post a ser deletado.
     * @throws RegraNegocioException Se o post com o ID fornecido não for encontrado.
     */
    void deletarPost(Integer id);

    /**
     * Filtra posts do fórum com base em critérios específicos.
     * <p>
     * Este método permite filtrar posts por tags, username do perfil ou nome do livro.
     * Se nenhum filtro for fornecido, retorna todos os posts (equivalente a listarTodos).
     * </p>
     *
     * @param tags      Tags associadas ao post (opcional).
     * @param username  Nome de usuário do perfil que criou o post (opcional).
     * @param livroNome Nome do livro associado ao post (opcional).
     * @param page      Número da página a ser retornada.
     * @param size      Quantidade de posts por página.
     * @return Página de PostForumResponseDTO contendo os posts que correspondem aos filtros.
     */
    Page<PostForumResponseDTO> filtrarPosts(String tags, String username, String livroNome, int page, int size);
}