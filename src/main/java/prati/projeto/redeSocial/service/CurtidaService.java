package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.exception.RegraNegocioException;

public interface CurtidaService {

    /**
     * Permite que um perfil curta um comentário.
     * <p>
     * Este método adiciona o ID do perfil à lista de perfis que curtiram o comentário
     * e incrementa a contagem de curtidas do comentário. Se o perfil já tiver curtido
     * o comentário, uma exceção será lançada.
     * </p>
     *
     * @param perfilId    ID do perfil que está curtindo o comentário.
     * @param comentarioId ID do comentário que será curtido.
     * @throws RegraNegocioException Se o perfil já tiver curtido o comentário ou
     *                               se o perfil ou comentário não forem encontrados.
     */
    void curtirComentario(Integer perfilId, Integer comentarioId);

    /**
     * Permite que um perfil descurta um comentário.
     * <p>
     * Este método remove o ID do perfil da lista de perfis que curtiram o comentário
     * e decrementa a contagem de curtidas do comentário. Se o perfil não tiver curtido
     * o comentário anteriormente, uma exceção será lançada.
     * </p>
     *
     * @param perfilId    ID do perfil que está descurtindo o comentário.
     * @param comentarioId ID do comentário que será descurtido.
     * @throws RegraNegocioException Se o perfil não tiver curtido o comentário ou
     *                               se o perfil ou comentário não forem encontrados.
     */
    void descurtirComentario(Integer perfilId, Integer comentarioId);

    /**
     * Permite que um perfil curta uma resposta a um comentário.
     * <p>
     * Este método adiciona o ID do perfil à lista de perfis que curtiram a resposta
     * e incrementa a contagem de curtidas da resposta. Se o perfil já tiver curtido
     * a resposta, uma exceção será lançada.
     * </p>
     *
     * @param perfilId  ID do perfil que está curtindo a resposta.
     * @param respostaId ID da resposta que será curtida.
     * @throws RegraNegocioException Se o perfil já tiver curtido a resposta ou
     *                               se o perfil ou resposta não forem encontrados.
     */
    void curtirResposta(Integer perfilId, Integer respostaId);

    /**
     * Permite que um perfil descurta uma resposta a um comentário.
     * <p>
     * Este método remove o ID do perfil da lista de perfis que curtiram a resposta
     * e decrementa a contagem de curtidas da resposta. Se o perfil não tiver curtido
     * a resposta anteriormente, uma exceção será lançada.
     * </p>
     *
     * @param perfilId  ID do perfil que está descurtindo a resposta.
     * @param respostaId ID da resposta que será descurtida.
     * @throws RegraNegocioException Se o perfil não tiver curtido a resposta ou
     *                               se o perfil ou resposta não forem encontrados.
     */
    void descurtirResposta(Integer perfilId, Integer respostaId);

    /**
     * Permite que um perfil curta um comentário no fórum.
     * <p>
     * Este método adiciona o ID do perfil à lista de perfis que curtiram o comentário do fórum,
     * incrementa a contagem de curtidas e envia uma notificação ao autor do comentário.
     * </p>
     *
     * @param perfilId          ID do perfil que está curtindo.
     * @param comentarioForumId ID do comentário do fórum a ser curtido.
     * @throws RegraNegocioException Se o perfil já tiver curtido o comentário ou
     *                               se o perfil/comentário não forem encontrados.
     */
    void curtirComentarioForum(Integer perfilId, Integer comentarioForumId);

    /**
     * Permite que um perfil descurta um comentário no fórum.
     * <p>
     * Este método remove o ID do perfil da lista de curtidas do comentário do fórum
     * e decrementa a contagem de curtidas.
     * </p>
     *
     * @param perfilId          ID do perfil que está descurtindo.
     * @param comentarioForumId ID do comentário do fórum a ser descurtido.
     * @throws RegraNegocioException Se o perfil não tiver curtido o comentário ou
     *                               se o perfil/comentário não forem encontrados.
     */
    void descurtirComentarioForum(Integer perfilId, Integer comentarioForumId);

    /**
     * Permite que um perfil curta uma resposta no fórum.
     * <p>
     * Este método adiciona o ID do perfil à lista de perfis que curtiram a resposta do fórum,
     * incrementa a contagem de curtidas e envia uma notificação ao autor da resposta.
     * </p>
     *
     * @param perfilId       ID do perfil que está curtindo.
     * @param respostaForumId ID da resposta do fórum a ser curtida.
     * @throws RegraNegocioException Se o perfil já tiver curtido a resposta ou
     *                               se o perfil/resposta não forem encontrados.
     */
    void curtirRespostaForum(Integer perfilId, Integer respostaForumId);

    /**
     * Permite que um perfil descurta uma resposta no fórum.
     * <p>
     * Este método remove o ID do perfil da lista de curtidas da resposta do fórum
     * e decrementa a contagem de curtidas.
     * </p>
     *
     * @param perfilId       ID do perfil que está descurtindo.
     * @param respostaForumId ID da resposta do fórum a ser descurtida.
     * @throws RegraNegocioException Se o perfil não tiver curtido a resposta ou
     *                               se o perfil/resposta não forem encontrados.
     */
    void descurtirRespostaForum(Integer perfilId, Integer respostaForumId);

    /**
     * Permite que um perfil curta um post no fórum.
     * <p>
     * Este método adiciona o ID do perfil à lista de perfis que curtiram o post,
     * incrementa a contagem de curtidas e envia uma notificação ao autor do post.
     * </p>
     *
     * @param perfilId ID do perfil que está curtindo.
     * @param postId    ID do post a ser curtido.
     * @throws RegraNegocioException Se o perfil já tiver curtido o post ou
     *                               se o perfil/post não forem encontrados.
     */
    void curtirPost(Integer perfilId, Integer postId);

    /**
     * Permite que um perfil descurta um post no fórum.
     * <p>
     * Este método remove o ID do perfil da lista de curtidas do post
     * e decrementa a contagem de curtidas.
     * </p>
     *
     * @param perfilId ID do perfil que está descurtindo.
     * @param postId    ID do post a ser descurtido.
     * @throws RegraNegocioException Se o perfil não tiver curtido o post ou
     *                               se o perfil/post não forem encontrados.
     */
    void descurtirPost(Integer perfilId, Integer postId);
}