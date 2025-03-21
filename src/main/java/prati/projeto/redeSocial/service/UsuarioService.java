package prati.projeto.redeSocial.service;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;

public interface UsuarioService {

    /**
     * Recupera as informações resumidas de um usuário com base no seu email.
     *
     * @param email O email do usuário a ser buscado.
     * @return Um objeto {@link UsuarioResumidoDTO} contendo o nome de usuário (username) e o email do usuário.
     * @throws RegraNegocioException Caso o usuário não seja encontrado com o email fornecido.
     */
    UsuarioResumidoDTO getUsuarioByEmail(String email);

    /**
     * Registra um novo usuário no sistema.
     *
     * Valida se o email e o nome de usuário não estão já cadastrados no sistema antes de salvar.
     *
     * @param usuario O objeto {@link Usuario} contendo as informações do usuário a ser salvo.
     * @return O objeto {@link Usuario} salvo, com todas as informações persistidas no banco de dados, incluindo o ID.
     * @throws RegraNegocioException Caso o email ou o nome de usuário já estejam cadastrados no sistema.
     */
    Usuario saveUsuario(Usuario usuario);

    /**
     * Remove um usuário do sistema com base no seu email.
     *
     * @param email O email do usuário a ser removido.
     * @throws RegraNegocioException Caso o usuário não seja encontrado no sistema.
     */
    void deleteUsuario(String email);

    /**
     * Atualiza as informações de um usuário existente no sistema.
     *
     * A atualização é permitida apenas para os campos que não envolvem mudanças no nome de usuário,
     * já que alterações no username não são permitidas.
     *
     * @param email O email do usuário a ser atualizado.
     * @param usuario O objeto {@link Usuario} contendo as novas informações a serem atualizadas.
     * @throws RegraNegocioException Caso o usuário não seja encontrado ou tente alterar o nome de usuário.
     */
    void updateUsuario(String email, Usuario usuario);

    /**
     * Gera e envia um token para redefinição de senha ao email do usuário.
     *
     * @param email O email do usuário que solicitou a redefinição de senha.
     * @throws RegraNegocioException Caso o usuário não seja encontrado no sistema.
     */
    void requestPasswordReset(String email);

    /**
     * Redefine a senha do usuário com base em um token de redefinição válido.
     *
     * @param token O token gerado para a redefinição de senha.
     * @param newPassword A nova senha escolhida pelo usuário.
     * @throws TokenInvalidException Caso o token seja inválido ou tenha expirado.
     * @throws RegraNegocioException Caso o usuário correspondente ao token não seja encontrado.
     */
    void resetPassword(String token, String newPassword);

    /**
     * Altera a senha de um usuário autenticado.
     *
     * @param email O email do usuário.
     * @param oldPassword A senha atual do usuário.
     * @param newPassword A nova senha escolhida pelo usuário.
     * @throws RegraNegocioException Caso a senha atual seja inválida.
     */
    void changePassword(String email, String oldPassword, String newPassword);
}