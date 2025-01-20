package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;

import java.util.List;

public interface ResenhaService {

    /**
     * Busca uma resenha pelo seu ID.
     *
     * @param id o ID da resenha a ser buscada
     * @return um objeto {@link ResenhaViewDTO} representando a resenha encontrada
     * @throws org.springframework.web.server.ResponseStatusException se a resenha não for encontrada
     */
    ResenhaViewDTO getResenhaById(Integer id);

    /**
     * Salva uma nova resenha.
     *
     * @param resenhaDTO um objeto {@link ResenhaDTO} contendo os dados da resenha
     * @return o ID da resenha criada
     * @throws org.springframework.web.server.ResponseStatusException se o usuário ou livro associado não forem encontrados
     *                                                                 ou se a nota estiver fora do intervalo permitido (0 a 5)
     */
    Integer saveResenha(ResenhaDTO resenhaDTO);

    /**
     * Exclui uma resenha pelo seu ID.
     *
     * @param id o ID da resenha a ser excluída
     * @throws org.springframework.web.server.ResponseStatusException se a resenha não for encontrada
     */
    void deleteResenha(Integer id);

    /**
     * Atualiza uma resenha existente.
     *
     * @param id o ID da resenha a ser atualizada
     * @param resenhaDTO um objeto {@link ResenhaDTO} contendo os novos dados da resenha
     * @throws org.springframework.web.server.ResponseStatusException se a resenha, o usuário ou o livro não forem encontrados
     */
    void updateResenha(Integer id, ResenhaDTO resenhaDTO);

    /**
     * Busca todas as resenhas associadas a um determinado livro.
     *
     * @param livroId o ID do livro cujas resenhas serão buscadas
     * @return uma lista de objetos {@link ResenhaViewDTO} representando as resenhas encontradas
     * @throws org.springframework.web.server.ResponseStatusException se o livro não for encontrado
     */
    List<ResenhaViewDTO> findByLivro(Integer livroId);

    /**
     * Busca todas as resenhas de todos os usuários.
     *
     * @return uma lista de objetos {@link ResenhaViewDTO} representando todas as resenhas
     */
    List<ResenhaViewDTO> findAllResenhas();
}
