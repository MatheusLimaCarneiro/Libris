package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;

import java.util.Set;

public interface RelacionamentoSeguidoresService {

    /**
     * Faz um perfil seguir outro perfil.
     * <p>
     * Valida se os IDs dos perfis são diferentes e se o seguidor ainda não está seguindo o seguido.
     * Se o relacionamento já existir, uma {@link RegraNegocioException} será lançada.
     * </p>
     *
     * @param seguidorId ID do perfil que deseja seguir.
     * @param seguidoId  ID do perfil a ser seguido.
     * @throws RegraNegocioException Se o usuário tentar seguir a si mesmo ou se já estiver seguindo o perfil.
     */
    void seguirPerfil(Integer seguidorId, Integer seguidoId);

    /**
     * Faz um perfil deixar de seguir outro perfil.
     * <p>
     * Remove a relação de seguimento entre os perfis, caso exista.
     * </p>
     *
     * @param seguidorId ID do perfil que deseja deixar de seguir.
     * @param seguidoId  ID do perfil que será deixado de seguir.
     */
    void deixarDeSeguir(Integer seguidorId, Integer seguidoId);

    /**
     * Verifica se um perfil está seguindo outro perfil.
     *
     * @param seguidorId ID do perfil seguidor.
     * @param seguidoId  ID do perfil seguido.
     * @return {@code true} se o perfil seguidor está seguindo o perfil seguido, caso contrário {@code false}.
     */
    boolean estaSeguindo(Integer seguidorId, Integer seguidoId);

    /**
     * Retorna uma lista de seguidores de um perfil.
     * <p>
     * Lista todos os perfis que seguem o perfil especificado.
     * </p>
     *
     * @param perfilId ID do perfil cujos seguidores serão buscados.
     * @return Um conjunto de {@link PerfilResumidoDTO} representando os seguidores.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    Set<PerfilResumidoDTO> buscarSeguidores(Integer perfilId);

    /**
     * Retorna uma lista de perfis que um perfil está seguindo.
     * <p>
     * Lista todos os perfis seguidos pelo perfil especificado.
     * </p>
     *
     * @param perfilId ID do perfil cujos seguidos serão buscados.
     * @return Um conjunto de {@link PerfilResumidoDTO} representando os perfis seguidos.
     * @throws RegraNegocioException Se o perfil não for encontrado.
     */
    Set<PerfilResumidoDTO> buscarSeguindo(Integer perfilId);
}
