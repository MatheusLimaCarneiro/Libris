package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;

import java.util.List;

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
     * Lista todas as avaliações associadas a uma resenha.
     * <p>
     * Este método recebe o ID da resenha e busca todas as avaliações associadas a ela no banco de dados.
     * Retorna uma lista de DTOs contendo os dados de cada avaliação.
     * </p>
     *
     * @param resenhaId ID da resenha para a qual as avaliações serão buscadas.
     * @return Lista de AvaliacaoDTO contendo os dados das avaliações associadas à resenha.
     * @throws RegraNegocioException Se a resenha não for encontrada.
     */
    List<AvaliacaoDTO> listarAvaliacaoPorResenha(Integer resenhaId);

    /**
     * Atualiza uma avaliação existente.
     * <p>
     * Este método recebe o ID da avaliação e um DTO contendo os dados atualizados (texto e nota).
     * Atualiza a avaliação no banco de dados e retorna os dados da avaliação atualizada.
     * </p>
     *
     * @param avaliacaoId  ID da avaliação a ser atualizada.
     * @param avaliacaoDTO DTO contendo os dados atualizados da avaliação (texto e nota).
     * @return AvaliacaoDTO contendo os dados da avaliação atualizada, incluindo perfil, texto e nota.
     * @throws RegraNegocioException Se a avaliação não for encontrada.
     */
    AvaliacaoDTO editarAvaliacao(Integer avaliacaoId, AvaliacaoDTO avaliacaoDTO);

    /**
     * Deleta uma avaliação.
     * <p>
     * Este método recebe o ID da avaliação e a remove do banco de dados.
     * </p>
     *
     * @param avaliacaoId ID da avaliação a ser deletada.
     * @throws RegraNegocioException Se a avaliação não for encontrada.
     */
    void deletarAvaliacao(Integer avaliacaoId);

    /**
     * Lista todas as avaliações feitas por um perfil.
     * <p>
     * Este método recebe o ID do perfil e busca todas as avaliações feitas por ele no banco de dados.
     * Retorna uma lista de DTOs contendo os dados de cada avaliação.
     * </p>
     *
     * @param perfilId ID do perfil para o qual as avaliações serão buscadas.
     * @return Lista de AvaliacaoDTO contendo os dados das avaliações feitas pelo perfil.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    List<AvaliacaoDTO> listarAvaliacoesPorPerfil(Integer perfilId);
}
