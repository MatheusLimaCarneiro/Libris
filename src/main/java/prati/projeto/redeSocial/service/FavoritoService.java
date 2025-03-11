package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.rest.dto.FavoritoRequestDTO;
import prati.projeto.redeSocial.rest.dto.FavoritoResponseDTO;

public interface FavoritoService {

    /**
     * Busca um favorito pelo seu ID.
     * <p>
     * Este método retorna os detalhes de um favorito específico identificado pelo ID fornecido.
     * </p>
     *
     * @param id ID do favorito a ser buscado.
     * @return FavoritoResponseDTO contendo os dados do favorito, incluindo usuário, livro e data.
     * @throws RegraNegocioException Se o favorito com o ID fornecido não for encontrado.
     */
    FavoritoResponseDTO getFavoritoById(Integer id);

    /**
     * Adiciona um livro aos favoritos de um perfil.
     * <p>
     * Este método persiste um novo registro de favorito associando um perfil a um livro.
     * Valida se o livro já foi favoritado pelo perfil antes de salvar.
     * </p>
     *
     * @param dto DTO contendo o ID do perfil e o Google ID do livro a ser favoritado.
     * @return FavoritoResponseDTO com os dados do favorito recém-criado.
     * @throws RegraNegocioException Se o livro já estiver favoritado pelo perfil,
     *                               ou se o perfil/livro não forem encontrados.
     */
    FavoritoResponseDTO favoritarLivro(FavoritoRequestDTO dto);

    /**
     * Remove um livro dos favoritos de um perfil.
     * <p>
     * Este método exclui permanentemente o registro de favorito identificado pelo ID.
     * </p>
     *
     * @param id ID do favorito a ser removido.
     * @throws RegraNegocioException Se o favorito com o ID fornecido não for encontrado.
     */
    void desfavoritarLivro(Integer id);

    /**
     * Lista todos os favoritos de um perfil específico.
     * <p>
     * Retorna uma lista paginada de favoritos associados ao perfil identificado pelo ID.
     * </p>
     *
     * @param perfilId ID do perfil cujos favoritos serão listados.
     * @param page     Número da página a ser retornada.
     * @param size     Quantidade de itens por página.
     * @return Página de FavoritoResponseDTO contendo os favoritos do perfil.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    Page<FavoritoResponseDTO> listarFavoritosPorPerfil(Integer perfilId, int page, int size);

    /**
     * Lista todos os favoritos registrados no sistema.
     * <p>
     * Retorna uma lista paginada com todos os favoritos, independente do perfil ou livro.
     * </p>
     *
     * @param page Número da página a ser retornada.
     * @param size Quantidade de itens por página.
     * @return Página de FavoritoResponseDTO contendo todos os favoritos.
     */
    Page<FavoritoResponseDTO> listarTodosFavoritos(int page, int size);

    /**
     * Filtra favoritos com base em critérios combinados.
     * <p>
     * Permite filtrar favoritos por username do perfil, Google ID do livro, ou ambos.
     * Se nenhum filtro for fornecido, retorna todos os favoritos (equivalente a listarTodosFavoritos).
     * </p>
     *
     * @param username Nome de usuário do perfil para filtro (opcional).
     * @param googleId Google ID do livro para filtro (opcional).
     * @param page     Número da página a ser retornada.
     * @param size     Quantidade de itens por página.
     * @return Página de FavoritoResponseDTO contendo os favoritos que correspondem aos filtros.
     * @throws RegraNegocioException Se o usuário (username) ou livro (googleId) não forem encontrados.
     */
    Page<FavoritoResponseDTO> filtrarFavoritos(String username, String googleId, int page, int size);
}