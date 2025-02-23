package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;

import java.util.List;

public interface LivroService {

    /**
     * Busca um livro pelo ID.
     * <p>
     * Retorna os dados do livro correspondente ao ID fornecido. Caso o livro não seja encontrado,
     * uma exceção do tipo LivroException será lançada.
     * </p>
     *
     * @param id ID do livro a ser buscado.
     * @return A entidade Livro correspondente ao ID fornecido.
     * @throws LivroException Se o livro com o ID fornecido não for encontrado.
     */
    Livro getLivroById(Integer id);

    /**
     * Salva um novo livro no sistema.
     * <p>
     * O livro será salvo após a validação de que o ISBN fornecido é único. Caso o ISBN já exista,
     * uma exceção do tipo LivroException será lançada.
     * </p>
     *
     * @param livro Entidade Livro contendo os dados a serem persistidos.
     * @return A entidade Livro salva com os dados armazenados no banco.
     * @throws LivroException Se o ISBN já estiver cadastrado.
     */
    Livro saveLivro(Livro livro);

    /**
     * Exclui um livro pelo ID.
     * <p>
     * Remove permanentemente um livro do sistema pelo ID fornecido. Se o livro não existir,
     * uma exceção do tipo LivroException será lançada.
     * </p>
     *
     * @param id ID do livro a ser excluído.
     * @throws LivroException Se o livro com o ID fornecido não for encontrado.
     */
    void deleteLivro(Integer id);

    /**
     * Atualiza os dados de um livro existente.
     * <p>
     * Permite a atualização dos dados do livro pelo ID. O método verifica se o ISBN é único antes da atualização
     * e lança uma exceção caso o livro não seja encontrado ou o ISBN já esteja cadastrado.
     * </p>
     *
     * @param id    ID do livro a ser atualizado.
     * @param livro Entidade Livro contendo os novos dados.
     * @throws LivroException Se o livro com o ID fornecido não for encontrado.
     * @throws LivroException Se o ISBN já estiver cadastrado.
     */
    void updateLivro(Integer id, Livro livro);

    /**
     * Busca livros com base no título e/ou autores.
     * <p>
     * Realiza uma busca filtrada considerando os critérios informados. Se ambos os parâmetros forem fornecidos,
     * a busca retornará livros que correspondam a ambos. Se apenas um for informado, a busca será feita apenas com base nesse critério.
     * A pesquisa é case-insensitive e permite correspondência parcial de strings.
     * </p>
     *
     * @param titulo  Título (ou parte do título) do livro para busca.
     * @param autores Nome (ou parte do nome) dos autores do livro para busca.
     * @return Lista de livros que atendem aos critérios especificados.
     */
    List<Livro> findLivro(String titulo, String autores);

    /**
     * Retorna todos os livros cadastrados no sistema.
     * <p>
     * Este método busca e retorna uma página contendo os livros armazenados no banco de dados, com base nos parâmetros
     * de paginação fornecidos.
     * </p>
     *
     * @param page O número da página (começando de 0).
     * @param size O tamanho da página (quantidade de livros por página).
     * @return Uma página de objetos LivroResumidoDTO representando todos os livros cadastrados.
     */
    Page<LivroResumidoDTO> getAllLivros(int page, int size);
}
