package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;

public interface AvaliacaoService {

    /**
     * Adiciona uma nova avaliação a uma resenha.
     * <p>
     * Este método recebe o ID da resenha e um DTO contendo os dados da avaliação (perfil, texto e nota).
     * Persiste a avaliação na base de dados e retorna os dados da avaliação salva.
     * </p>
     *
     * @param resenhaId    ID da resenha à qual a avaliação será associada.
     * @param avaliacaoDTO DTO contendo os dados da nova avaliação (perfil, texto e nota).
     * @return AvaliacaoDTO contendo os dados da avaliação salva, incluindo perfil, texto e nota.
     * @throws RegraNegocioException Se o perfil já tiver avaliado a resenha ou se o perfil ou a resenha não forem encontrados.
     */
    AvaliacaoDTO adicionarAvaliacao(Integer resenhaId, AvaliacaoDTO avaliacaoDTO);

    /**
     * Lista as avaliações associadas a uma resenha.
     * <p>
     * Este método recebe o ID da resenha e retorna uma página de avaliações associadas a ela.
     * A página contém as avaliações paginadas, ou seja, você pode especificar o número da página e o tamanho da página.
     * </p>
     *
     * @param resenhaId ID da resenha para a qual as avaliações serão buscadas.
     * @param page      Número da página a ser retornada.
     * @param size      Tamanho da página (quantidade de avaliações por página).
     * @return Uma página de AvaliacaoDTO contendo os dados das avaliações associadas à resenha.
     * @throws RegraNegocioException Se a resenha não for encontrada.
     */
    Page<AvaliacaoDTO> listarAvaliacaoPorResenha(Integer resenhaId, int page, int size);


    /**
     * Atualiza uma avaliação existente de uma resenha.
     *
     * @param resenhaId   ID da resenha à qual a avaliação pertence.
     * @param avaliacaoId ID da avaliação a ser atualizada.
     * @param avaliacaoDTO DTO contendo os dados atualizados da avaliação (texto e nota).
     * @return AvaliacaoDTO contendo os dados da avaliação atualizada, incluindo perfil, texto e nota.
     * @throws RegraNegocioException Se a avaliação não for encontrada ou não pertencer à resenha informada.
     */
    AvaliacaoDTO editarAvaliacao(Integer resenhaId, Integer avaliacaoId, AvaliacaoDTO avaliacaoDTO);

    /**
     * Deleta uma avaliação.
     * <p>
     * Este método recebe o ID da avaliação e a remove do banco de dados.
     * </p>
     *
     * @param resenhaId   ID da resenha à qual a avaliação pertence.
     * @param avaliacaoId ID da avaliação a ser deletada.
     * @throws RegraNegocioException Se a avaliação não for encontrada ou não pertencer à resenha informada.
     */
    void deletarAvaliacao(Integer resenhaId, Integer avaliacaoId);

    /**
     * Lista as avaliações feitas por um perfil.
     * <p>
     * Este método recebe o ID do perfil e retorna uma página de avaliações feitas por esse perfil.
     * A página contém as avaliações paginadas, ou seja, você pode especificar o número da página e o tamanho da página.
     * </p>
     *
     * @param perfilId ID do perfil para o qual as avaliações serão buscadas.
     * @param page     Número da página a ser retornada.
     * @param size     Tamanho da página (quantidade de avaliações por página).
     * @return Uma página de AvaliacaoDTO contendo os dados das avaliações feitas pelo perfil.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    Page<AvaliacaoDTO> listarAvaliacoesPorPerfil(Integer perfilId, int page, int size);

}
