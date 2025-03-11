package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.NotificacaoDTO;
import prati.projeto.redeSocial.exception.RegraNegocioException;

public interface NotificacaoService {

    /**
     * Cria uma nova notificação para um destinatário.
     * <p>
     * Este método cria uma notificação associada a um destinatário e um remetente,
     * com uma mensagem e um tipo específico. A notificação é persistida no banco de dados.
     * </p>
     *
     * @param destinatario Perfil do destinatário da notificação.
     * @param remetente    Perfil do remetente da notificação.
     * @param mensagem     Mensagem da notificação.
     * @param tipo         Tipo da notificação (ex.: "curtida", "comentário").
     * @return NotificacaoDTO contendo os dados da notificação criada.
     * @throws RegraNegocioException Se o destinatário ou remetente não forem encontrados.
     */
    NotificacaoDTO criarNotificacao(Perfil destinatario, Perfil remetente, String mensagem, String tipo);

    /**
     * Busca notificações associadas a um perfil específico.
     * <p>
     * Este método retorna uma lista paginada de notificações destinadas ao perfil
     * identificado pelo ID fornecido.
     * </p>
     *
     * @param perfilId ID do perfil destinatário.
     * @param page     Número da página a ser retornada.
     * @param size     Quantidade de notificações por página.
     * @return Página de NotificacaoDTO contendo as notificações do perfil.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    Page<NotificacaoDTO> buscarNotificacoesPorPerfil(Integer perfilId, int page, int size);

    /**
     * Busca notificações associadas a um perfil específico, identificado pelo username.
     * <p>
     * Este método retorna uma lista paginada de notificações destinadas ao perfil
     * associado ao username fornecido.
     * </p>
     *
     * @param username Nome de usuário do perfil destinatário.
     * @param page     Número da página a ser retornada.
     * @param size     Quantidade de notificações por página.
     * @return Página de NotificacaoDTO contendo as notificações do perfil.
     * @throws RegraNegocioException Se o usuário não for encontrado.
     */
    Page<NotificacaoDTO> buscarNotificacoesPorUsername(String username, int page, int size);

    /**
     * Deleta uma notificação específica.
     * <p>
     * Este método remove uma notificação do banco de dados, desde que o perfil
     * que está solicitando a exclusão seja o destinatário da notificação.
     * </p>
     *
     * @param id       ID da notificação a ser deletada.
     * @param perfilId ID do perfil que está solicitando a exclusão.
     * @throws RegraNegocioException Se a notificação não for encontrada ou
     *                               se o perfil não for o destinatário da notificação.
     */
    void deletarNotificacao(Integer id, Integer perfilId);

    /**
     * Marca uma notificação como lida.
     * <p>
     * Este método atualiza o status de uma notificação para "lida", desde que o perfil
     * que está solicitando a ação seja o destinatário da notificação.
     * </p>
     *
     * @param id       ID da notificação a ser marcada como lida.
     * @param perfilId ID do perfil que está solicitando a ação.
     * @throws RegraNegocioException Se a notificação não for encontrada ou
     *                               se o perfil não for o destinatário da notificação.
     */
    void marcarComoLida(Integer id, Integer perfilId);

    /**
     * Marca todas as notificações de um perfil como lidas.
     * <p>
     * Este método atualiza o status de todas as notificações destinadas ao perfil
     * identificado pelo ID fornecido para "lidas".
     * </p>
     *
     * @param perfilId ID do perfil destinatário.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    void marcarTodasComoLida(Integer perfilId);

    /**
     * Deleta todas as notificações de um perfil específico.
     * <p>
     * Este método remove todas as notificações destinadas ao perfil identificado pelo ID fornecido.
     * </p>
     *
     * @param perfilId ID do perfil destinatário.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    void deletarTodasNotificacoes(Integer perfilId);

    /**
     * Deleta todas as notificações de um perfil específico, identificado pelo username.
     * <p>
     * Este método remove todas as notificações destinadas ao perfil associado ao username fornecido.
     * </p>
     *
     * @param username Nome de usuário do perfil destinatário.
     * @throws RegraNegocioException Se o usuário não for encontrado.
     */
    void deletarTodasNotificacoesPorUsername(String username);
}