package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.RespostaDTO;

import java.util.List;

public interface ComentarioRespostaService {

    /**
     * Adiciona uma nova resposta a um comentário existente.
     * <p>
     * Este método recebe o ID do comentário e um DTO contendo os dados da nova resposta
     * (texto do usuário). Persiste a resposta na base de dados e retorna os dados da resposta salva.
     * </p>
     *
     * @param comentarioId ID do comentário ao qual a resposta será associada.
     * @param respostaDTO  DTO contendo os dados da nova resposta (texto do usuário).
     * @return RespostaDTO contendo os dados da resposta salva, incluindo ID, usuário, texto e data da resposta.
     * @throws RegraNegocioException Se o comentário não for encontrado ou o usuário não for encontrado.
     */
    RespostaDTO adicionarResposta(Integer comentarioId, RespostaDTO respostaDTO);

    /**
     * Lista todas as respostas associadas a um comentário.
     * <p>
     * Este método recebe o ID do comentário e busca todas as respostas associadas a ele no banco de dados.
     * Retorna uma lista de DTOs contendo os dados de cada resposta.
     * </p>
     *
     * @param comentarioId ID do comentário para o qual as respostas serão buscadas.
     * @return Lista de RespostaDTO contendo os dados das respostas associadas ao comentário.
     */
    List<RespostaDTO> listarRespostasPorComentario(Integer comentarioId);

    /**
     * Atualiza uma resposta existente a um comentário.
     * <p>
     * Este método recebe o ID da resposta e um DTO contendo os dados atualizados (texto do usuário).
     * Atualiza a resposta no banco de dados e retorna os dados da resposta atualizada.
     * </p>
     *
     * @param respostaId ID da resposta a ser atualizada.
     * @param respostaDTO DTO contendo os dados atualizados da resposta (texto do usuário).
     * @return RespostaDTO contendo os dados da resposta atualizada, incluindo ID, usuário, texto e data da resposta.
     * @throws RegraNegocioException Se a resposta não for encontrada.
     */
    RespostaDTO atualizarResposta(Integer respostaId, RespostaDTO respostaDTO);

    /**
     * Deleta uma resposta a um comentário.
     * <p>
     * Este método recebe o ID da resposta e a remove do banco de dados.
     * </p>
     *
     * @param respostaId ID da resposta a ser deletada.
     * @throws RegraNegocioException Se a resposta não for encontrada.
     */
    void deletarResposta(Integer respostaId);
}
