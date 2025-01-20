package prati.projeto.redeSocial.service;
import prati.projeto.redeSocial.modal.entity.Usuario;

public interface UsuarioService {

    /**
     * Busca um usuário pelo email.
     *
     * @param email Email do usuário a ser buscado.
     * @return Objeto Usuario correspondente ao email informado.
     * @throws RegraNegocioException se o usuário não for encontrado.
     */
    Usuario getUsuarioByEmail(String email);

    /**
     * Salva um novo usuário no sistema.
     *
     * @param usuario Objeto Usuario contendo as informações a serem salvas.
     * @return O objeto Usuario salvo.
     * @throws RegraNegocioException se o email ou username já estiverem cadastrados.
     */
    Usuario saveUsuario(Usuario usuario);

    /**
     * Remove um usuário do sistema.
     *
     * @param email Email do usuário a ser removido.
     * @throws RegraNegocioException se o usuário não for encontrado.
     */
    void deleteUsuario(String email);

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param email Email do usuário a ser atualizado.
     * @param usuario Objeto Usuario contendo as novas informações.
     * @throws RegraNegocioException se o usuário não for encontrado.
     */
    void updateUsuario(String email, Usuario usuario);
}
