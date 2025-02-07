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
}
