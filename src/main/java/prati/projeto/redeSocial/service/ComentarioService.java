package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.ComentarioDTO;
import prati.projeto.redeSocial.modal.entity.Comentario;

import java.util.List;

public interface ComentarioService {

    /**
     * Salva um novo comentário no sistema.
     * <p>
     * Este método recebe os dados do comentário em formato DTO, valida os dados, converte os objetos
     * necessários e os persiste no banco de dados.
     * </p>
     *
     * @param dto - Objeto DTO contendo os dados do comentário, como texto, ID de usuário, ID de livro e avaliação.
     * @return A entidade Comentario salva, com os dados persistidos, incluindo a avaliação e as referências ao usuário e livro.
     * @throws RegraNegocioException Se algum dado necessário não for encontrado (por exemplo, usuário ou livro inexistente) ou se a avaliação não for válida.
     */
    Comentario salvar(ComentarioDTO dto);

    /**
     * Lista todos os comentários presentes no sistema.
     * <p>
     * Este método retorna uma lista de todos os comentários armazenados no banco de dados, convertendo cada
     * entidade Comentario para seu respectivo DTO para que os dados sejam acessíveis de forma estruturada e adequada.
     * </p>
     *
     * @return Lista de objetos ComentarioDTO contendo as informações dos comentários, como ID, texto, usuário, livro e avaliação.
     */
    List<ComentarioDTO> listarTodos();

    /**
     * Busca um comentário pelo seu ID.
     * <p>
     * Este método retorna um comentário específico identificado pelo ID, convertendo a entidade para o formato DTO.
     * Caso o comentário não seja encontrado, uma exceção é lançada.
     * </p>
     *
     * @param id ID do comentário a ser buscado.
     * @return ComentarioDTO - contendo os dados do comentário.
     * @throws RuntimeException Se o comentário com o ID fornecido não for encontrado.
     */
    ComentarioDTO buscarPorId(Integer id);
}
