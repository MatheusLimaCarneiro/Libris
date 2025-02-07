package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.RespostaDTO;

public interface ComentarioRespostaService {

    /**
     * Adiciona uma nova resposta a um comentário existente.
     *
     * @param comentarioId ID do comentário ao qual a resposta será associada.
     * @param respostaDTO  DTO contendo os dados da nova resposta (texto do usuário).
     * @return RespostaDTO contendo os dados da resposta salva.
     * @throws RegraNegocioException Se o comentário ou o perfil não forem encontrados.
     */
    RespostaDTO adicionarResposta(Integer comentarioId, RespostaDTO respostaDTO);

    /**
     * Lista todas as respostas associadas a um comentário.
     *
     * @param comentarioId ID do comentário para o qual as respostas serão buscadas.
     * @param page         Número da página.
     * @param size         Tamanho da página.
     * @return Lista de RespostaDTO contendo os dados das respostas associadas ao comentário.
     */
    Page<RespostaDTO> listarRespostasPorComentario(Integer comentarioId, int page, int size);

    /**
     * Atualiza uma resposta existente a um comentário.
     *
     * @param respostaId  ID da resposta a ser atualizada.
     * @param respostaDTO DTO contendo os dados atualizados da resposta (texto do usuário).
     * @return RespostaDTO contendo os dados da resposta atualizada.
     * @throws RegraNegocioException Se a resposta não for encontrada.
     */
    RespostaDTO atualizarResposta(Integer respostaId, RespostaDTO respostaDTO);

    /**
     * Deleta uma resposta a um comentário.
     *
     * @param respostaId ID da resposta a ser deletada.
     * @throws RegraNegocioException Se a resposta não for encontrada.
     */
    void deletarResposta(Integer respostaId);

    /**
     * Busca uma resposta por ID.
     *
     * @param respostaId ID da resposta a ser buscada.
     * @return RespostaDTO contendo os dados da resposta.
     * @throws RegraNegocioException Se a resposta não for encontrada.
     */
    RespostaDTO buscarRespostaPorId(Integer respostaId);
}
