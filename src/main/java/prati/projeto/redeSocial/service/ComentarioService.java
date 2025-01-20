package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.ComentarioDTO;
import prati.projeto.redeSocial.modal.entity.Comentario;

import java.util.List;

public interface ComentarioService {

    /**
     * Salva um novo comentário no sistema.
     * <p>
     * Este método recebe os dados do comentário em formato DTO, realiza as validações necessárias,
     * converte os objetos para entidades, e persiste no banco de dados.
     * </p>
     *
     * @param dto Objeto DTO contendo os dados do comentário, como texto, ID do usuário, ID do livro e nota.
     * @return A entidade Comentario salva com os dados persistidos, incluindo a avaliação e as referências ao usuário e livro.
     * @throws RegraNegocioException Se algum dado necessário não for encontrado (ex.: usuário ou livro inexistente)
     *                               ou se a nota não for válida.
     */
    Comentario salvar(ComentarioDTO dto);

    /**
     * Lista todos os comentários presentes no sistema.
     * <p>
     * Este método retorna uma lista de todos os comentários armazenados no banco de dados,
     * convertendo cada entidade Comentario para seu respectivo DTO.
     * </p>
     *
     * @return Lista de objetos ComentarioDTO contendo as informações dos comentários, como ID, texto, usuário, livro e nota.
     */
    List<ComentarioDTO> listarTodos();

    /**
     * Busca um comentário pelo seu ID.
     * <p>
     * Este método busca um comentário específico identificado pelo ID fornecido.
     * Se o comentário não for encontrado, uma exceção é lançada.
     * </p>
     *
     * @param id ID do comentário a ser buscado.
     * @return ComentarioDTO contendo os dados do comentário, como texto, nota, data e informações relacionadas.
     * @throws RegraNegocioException Se o comentário com o ID fornecido não for encontrado.
     */
    ComentarioDTO buscarPorId(Integer id);

    /**
     * Atualiza um comentário existente, permitindo a modificação de texto e nota.
     * <p>
     * O método atualiza apenas os campos editáveis do comentário, preservando suas associações com o usuário e o livro.
     * As validações necessárias são realizadas antes de persistir as alterações.
     * </p>
     *
     * @param id  ID do comentário a ser atualizado.
     * @param dto Objeto DTO contendo os novos dados do comentário, como texto e nota.
     * @return ComentarioDTO atualizado com os novos dados.
     * @throws RegraNegocioException Se o comentário não for encontrado ou se a nota não for válida.
     */
    ComentarioDTO atualizarComentario(Integer id, ComentarioDTO dto);
}
