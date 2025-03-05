package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilRequestDTO;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;

public interface PerfilService {

    /**
     * Obtém um perfil pelo seu ID.
     * <p>
     * Este método busca um perfil específico identificado pelo ID fornecido.
     * Se o perfil não for encontrado, uma exceção {@link RegraNegocioException} é lançada.
     * </p>
     *
     * @param id ID do perfil a ser buscado.
     * @return PerfilDTO contendo os dados do perfil.
     * @throws RegraNegocioException Se o perfil com o ID fornecido não for encontrado.
     */
    PerfilDTO getPerfilById(Integer id);

    /**
     * Salva um novo perfil.
     * <p>
     * Este método recebe um objeto {@link PerfilRequestDTO} contendo os dados do novo perfil e o persiste no banco de dados.
     * Realiza validações como a obrigatoriedade do usuário e a existência de um perfil para o usuário.
     * </p>
     *
     * @param perfilRequestDTO DTO contendo os dados do novo perfil.
     * @return PerfilDTO contendo os dados do perfil salvo.
     * @throws RegraNegocioException Se o usuário associado ao perfil não for encontrado ou se já existir um perfil para o usuário.
     */
    PerfilDTO savePerfil(PerfilRequestDTO perfilRequestDTO);

    /**
     * Deleta um perfil pelo seu ID.
     * <p>
     * Este método remove um perfil do banco de dados com base no ID fornecido.
     * Se o perfil não for encontrado, uma exceção {@link RegraNegocioException} é lançada.
     * </p>
     *
     * @param id ID do perfil a ser deletado.
     * @throws RegraNegocioException Se o perfil com o ID fornecido não for encontrado.
     */
    void deletePerfil(Integer id);

    /**
     * Atualiza um perfil existente.
     * <p>
     * Este método atualiza os dados de um perfil existente com base no ID fornecido.
     * Recebe um objeto {@link PerfilRequestDTO} contendo os dados atualizados.
     * Se o perfil não for encontrado, uma exceção {@link RegraNegocioException} é lançada.
     * </p>
     *
     * @param id ID do perfil a ser atualizado.
     * @param perfilRequestDTO DTO contendo os dados atualizados do perfil.
     * @throws RegraNegocioException Se o perfil com o ID fornecido não for encontrado.
     */
    void updatePerfil(Integer id, PerfilRequestDTO perfilRequestDTO);

    /**
     * Retorna uma lista paginada de perfis.
     *
     * @param page Número da página.
     * @param size Tamanho da página.
     * @return Página contendo os perfis resumidos.
     */
    Page<PerfilResumidoDTO> listarPerfil(int page, int size);

    /**
     * Busca um perfil pelo username do usuário associado.
     * <p>
     * Este método busca um perfil com base no username do usuário.
     * Se o usuário ou o perfil não forem encontrados, uma exceção {@link RegraNegocioException} é lançada.
     * </p>
     *
     * @param username Nome de usuário associado ao perfil.
     * @return PerfilResumidoDTO contendo os dados resumidos do perfil.
     * @throws RegraNegocioException Se o usuário ou o perfil não forem encontrados.
     */
    PerfilResumidoDTO buscarPorUsername(String username);
}