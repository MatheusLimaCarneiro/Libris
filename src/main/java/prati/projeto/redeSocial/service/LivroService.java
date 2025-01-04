package prati.projeto.redeSocial.service;
import prati.projeto.redeSocial.modal.entity.Livro;

import java.util.List;

public interface LivroService {

    /**
     * Busca um livro pelo ID.
     * <p>
     * Este método retorna os dados do livro com o ID especificado. Caso não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id ID do livro a ser buscado.
     * @return A entidade Livro correspondente ao ID fornecido.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o livro com o ID fornecido não for encontrado.
     */
    Livro getLivroById(Integer id);

    /**
     * Salva um novo livro no sistema.
     * <p>
     * Este método salva um livro após validar que o ISBN fornecido é único. Caso o ISBN já exista,
     * uma exceção é lançada.
     * </p>
     *
     * @param livro Entidade Livro contendo os dados a serem salvos.
     * @return A entidade Livro salva com os dados persistidos.
     * @throws ResponseStatusException (HttpStatus.BAD_REQUEST) Se o ISBN já estiver cadastrado.
     */
    Livro saveLivro(Livro livro);

    /**
     * Deleta um livro pelo ID.
     * <p>
     * Este método remove do sistema o livro com o ID especificado. Caso o livro não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id ID do livro a ser deletado.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o livro com o ID fornecido não for encontrado.
     */
    void deleteLivro(Integer id);

    /**
     * Atualiza os dados de um livro existente.
     * <p>
     * Este método atualiza os dados de um livro pelo ID. Valida que o ISBN não é duplicado,
     * e lança uma exceção caso o livro não seja encontrado ou o ISBN seja duplicado.
     * </p>
     *
     * @param id    ID do livro a ser atualizado.
     * @param livro Entidade Livro contendo os dados atualizados.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o livro com o ID fornecido não for encontrado.
     * @throws ResponseStatusException (HttpStatus.BAD_REQUEST) Se o ISBN já estiver cadastrado.
     */
    void updateLivro(Integer id, Livro livro);

    /**
     * Busca livros com base em filtros fornecidos.
     * <p>
     * Este método utiliza um objeto de filtro para buscar livros que correspondam aos
     * critérios especificados. A pesquisa é case-insensitive e suporta correspondência parcial de strings.
     * </p>
     *
     * @param filtro Objeto Livro contendo os critérios de filtro.
     * @return Uma lista de livros que atendem aos critérios especificados.
     */
    List<Livro> findLivro(Livro filtro);
}
