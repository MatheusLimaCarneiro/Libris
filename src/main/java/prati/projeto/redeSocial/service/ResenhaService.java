package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;

public interface ResenhaService {

    /**
     * Busca uma resenha pelo seu ID.
     *
     * @param id o ID da resenha a ser buscada
     * @return um objeto {@link ResenhaViewDTO} representando a resenha encontrada
     * @throws RegraNegocioException se a resenha não for encontrada
     */
    ResenhaViewDTO getResenhaById(Integer id);

    /**
     * Salva uma nova resenha no sistema.
     *
     * @param resenhaDTO um objeto {@link ResenhaDTO} contendo os dados da resenha
     * @return um objeto {@link ResenhaViewDTO} representando a resenha salva
     * @throws RegraNegocioException se o perfil ou livro associado não forem encontrados,
     *         ou se a nota estiver fora do intervalo permitido (0 a 5)
     */
    ResenhaViewDTO saveResenha(ResenhaDTO resenhaDTO);

    /**
     * Exclui uma resenha pelo seu ID.
     *
     * @param id o ID da resenha a ser excluída
     * @throws RegraNegocioException se a resenha não for encontrada
     */
    void deleteResenha(Integer id);

    /**
     * Atualiza uma resenha existente com base nos novos dados fornecidos.
     *
     * @param id o ID da resenha a ser atualizada
     * @param resenhaDTO um objeto {@link ResenhaDTO} contendo os novos dados da resenha
     * @throws RegraNegocioException se a resenha, o perfil ou o livro não forem encontrados
     */
    void updateResenha(Integer id, ResenhaDTO resenhaDTO);

    /**
     * Busca todas as resenhas associadas a um livro, identificado pelo seu Google ID.
     *
     * @param googleId o Google ID do livro cujas resenhas serão buscadas
     * @param page o número da página a ser retornada (inicia em 0)
     * @param size o número de resenhas por página
     * @return uma página de objetos {@link ResenhaViewDTO} representando as resenhas encontradas
     * @throws RegraNegocioException se o livro não for encontrado
     */
    Page<ResenhaViewDTO> findByGoogleId(String googleId, int page, int size);

    /**
     * Busca todas as resenhas de todos os perfis, paginadas.
     *
     * @param page o número da página a ser retornada (inicia em 0)
     * @param size o número de resenhas por página
     * @return uma página de objetos {@link ResenhaViewDTO} representando todas as resenhas
     */
    Page<ResenhaViewDTO> findAllResenhas(Integer page, Integer size);
}