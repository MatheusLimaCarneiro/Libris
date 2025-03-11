package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.RespostaForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.exception.RegraNegocioException;

public interface RespostaForumService {

    /**
     * Cria uma nova resposta para um comentário no fórum.
     * <p>
     * Este método recebe o ID do comentário e os dados da resposta em formato DTO,
     * valida as informações, associa a resposta ao comentário e ao perfil, e persiste no banco de dados.
     * Além disso, envia uma notificação ao autor do comentário.
     * </p>
     *
     * @param comentarioForumId ID do comentário ao qual a resposta será associada.
     * @param dto               Objeto DTO contendo os dados da resposta, como texto e ID do perfil.
     * @return RespostaForumResponseDTO contendo os dados da resposta criada.
     * @throws RegraNegocioException Se o comentário ou perfil não forem encontrados.
     */
    RespostaForumResponseDTO criarRespostaForum(Integer comentarioForumId, RespostaForumRequestDTO dto);

    /**
     * Lista todas as respostas associadas a um comentário no fórum.
     * <p>
     * Este método retorna uma lista paginada de respostas associadas ao comentário
     * identificado pelo ID fornecido.
     * </p>
     *
     * @param comentarioForumId ID do comentário para o qual as respostas serão buscadas.
     * @param page              Número da página a ser retornada.
     * @param size              Quantidade de respostas por página.
     * @return Página de RespostaForumResponseDTO contendo as respostas do comentário.
     * @throws RegraNegocioException Se o comentário não for encontrado.
     */
    Page<RespostaForumResponseDTO> listarPorComentario(Integer comentarioForumId, int page, int size);

    /**
     * Deleta uma resposta de um comentário no fórum.
     * <p>
     * Este método remove permanentemente uma resposta identificada pelo ID fornecido,
     * desde que a resposta pertença ao comentário especificado.
     * </p>
     *
     * @param comentarioForumId ID do comentário ao qual a resposta pertence.
     * @param id                ID da resposta a ser deletada.
     * @throws RegraNegocioException Se a resposta não for encontrada ou
     *                               se não pertencer ao comentário informado.
     */
    void deletarRespostaForum(Integer comentarioForumId, Integer id);

    /**
     * Busca uma resposta específica de um comentário no fórum.
     * <p>
     * Este método retorna os detalhes de uma resposta identificada pelo ID fornecido,
     * desde que a resposta pertença ao comentário especificado.
     * </p>
     *
     * @param comentarioForumId ID do comentário ao qual a resposta pertence.
     * @param respostaId        ID da resposta a ser buscada.
     * @return RespostaForumResponseDTO contendo os dados da resposta.
     * @throws RegraNegocioException Se a resposta não for encontrada ou
     *                               se não pertencer ao comentário informado.
     */
    RespostaForumResponseDTO buscarRespostaForum(Integer comentarioForumId, Integer respostaId);
}