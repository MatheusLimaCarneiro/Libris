package prati.projeto.redeSocial.service;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;

public interface UsuarioService {

    /**
     * Recupera as informações resumidas de um usuário pelo seu email.
     *
     * @param email O email do usuário a ser buscado.
     * @return Um objeto {@link UsuarioResumidoDTO} contendo o username e o email do usuário.
     * @throws RegraNegocioException Caso o usuário não seja encontrado.
     */
    UsuarioResumidoDTO getUsuarioByEmail(String email);

    /**
     * Registra um novo usuário no sistema.
     *
     * @param usuario O objeto {@link Usuario} contendo as informações do usuário a ser salvo.
     * @return O objeto {@link Usuario} salvo, com todas as informações persistidas no banco de dados.
     * @throws RegraNegocioException Caso o email ou o username já estejam cadastrados.
     */
    Usuario saveUsuario(Usuario usuario);

    /**
     * Remove um usuário do sistema.
     *
     * @param email O email do usuário a ser removido.
     * @throws RegraNegocioException Caso o usuário não seja encontrado.
     */
    void deleteUsuario(String email);

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param email O email do usuário a ser atualizado.
     * @param usuario O objeto {@link Usuario} contendo as novas informações.
     * @throws RegraNegocioException Caso o usuário não seja encontrado.
     */
    void updateUsuario(String email, Usuario usuario);
}
