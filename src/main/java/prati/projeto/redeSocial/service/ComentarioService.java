package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.rest.dto.ComentarioDTO;

public interface ComentarioService {

    /**
     * Salva um novo comentário no sistema.
     * <p>
     * Este método recebe os dados do comentário em formato DTO, realiza as validações necessárias,
     * converte os objetos para entidades e persiste no banco de dados. O livro é referenciado pelo Google ID.
     * </p>
     *
     * @param dto Objeto DTO contendo os dados do comentário, como texto, ID do perfil, Google ID do livro e nota.
     * @return ComentarioDTO contendo os dados do comentário salvo.
     * @throws RegraNegocioException Se algum dado necessário não for encontrado (ex.: perfil ou livro inexistente)
     *                               ou se a nota não for válida.
     */
    ComentarioDTO salvar(ComentarioDTO dto) throws RegraNegocioException;

    /**
     * Lista todos os comentários presentes no sistema.
     * <p>
     * Este método retorna uma página de todos os comentários armazenados no banco de dados,
     * convertendo cada entidade Comentario para seu respectivo DTO.
     * </p>
     *
     * @param page Número da página a ser retornada.
     * @param size Quantidade de comentários por página.
     * @return Página de objetos ComentarioDTO contendo as informações dos comentários.
     */
    Page<ComentarioDTO> listarTodos(int page, int size);

    /**
     * Busca um comentário pelo seu ID.
     * <p>
     * Este método busca um comentário específico identificado pelo ID fornecido.
     * Se o comentário não for encontrado, uma exceção é lançada.
     * </p>
     *
     * @param id ID do comentário a ser buscado.
     * @return ComentarioDTO contendo os dados do comentário.
     * @throws RegraNegocioException Se o comentário com o ID fornecido não for encontrado.
     */
    ComentarioDTO buscarPorId(Integer id) throws RegraNegocioException;

    /**
     * Atualiza um comentário existente, permitindo a modificação de texto e nota.
     * <p>
     * O método atualiza apenas os campos editáveis do comentário, preservando suas associações com o perfil e o livro.
     * As validações necessárias são realizadas antes de persistir as alterações. O livro é referenciado pelo Google ID.
     * </p>
     *
     * @param id  ID do comentário a ser atualizado.
     * @param dto Objeto DTO contendo os novos dados do comentário, como texto e nota.
     * @return ComentarioDTO atualizado com os novos dados.
     * @throws RegraNegocioException Se o comentário não for encontrado, se o livro não for encontrado pelo Google ID
     *                               ou se a nota não for válida.
     */
    ComentarioDTO atualizarComentario(Integer id, ComentarioDTO dto) throws RegraNegocioException;

    /**
     * Exclui um comentário do sistema.
     * <p>
     * Este método remove permanentemente um comentário identificado pelo ID fornecido.
     * Se o comentário não for encontrado, uma exceção será lançada.
     * </p>
     *
     * @param id ID do comentário a ser excluído.
     * @throws RegraNegocioException Se o comentário com o ID fornecido não for encontrado.
     */
    void excluirComentario(Integer id) throws RegraNegocioException;

    /**
     * Lista todos os comentários de um livro específico.
     * <p>
     * Este método retorna uma página de comentários associados a um livro identificado pelo Google ID,
     * com base nos parâmetros de paginação fornecidos.
     * </p>
     *
     * @param googleId Google ID do livro cujos comentários serão listados.
     * @param page     Número da página a ser retornada.
     * @param size     Quantidade de comentários por página.
     * @return Página de objetos ComentarioDTO contendo os comentários relacionados ao livro.
     * @throws RegraNegocioException Se o livro com o Google ID fornecido não for encontrado.
     */
    Page<ComentarioDTO> listarPorLivro(String googleId, int page, int size) throws RegraNegocioException;

    Page<ComentarioDTO> listarComentariosPorUsername(String username, int page, int size);
}