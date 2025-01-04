package prati.projeto.redeSocial.service;
import prati.projeto.redeSocial.modal.entity.Usuario;

public interface UsuarioService {

    /**
     * Busca um usuário pelo ID.
     * <p>
     * Este método retorna os dados do usuário com o ID especificado. Caso não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id ID do usuário a ser buscado.
     * @return A entidade Usuario correspondente ao ID fornecido.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o usuário com o ID fornecido não for encontrado.
     */
    Usuario getUsuarioById(Integer id);

    /**
     * Salva um novo usuário no sistema.
     * <p>
     * Este método salva um usuário após validar que o email e o username fornecidos são únicos.
     * Caso já existam no sistema, uma exceção é lançada.
     * </p>
     *
     * @param usuario Entidade Usuario contendo os dados a serem salvos.
     * @return A entidade Usuario salva com os dados persistidos.
     * @throws ResponseStatusException (HttpStatus.BAD_REQUEST) Se o email ou username já estiverem cadastrados.
     */
    Usuario saveUsuario(Usuario usuario);

    /**
     * Deleta um usuário pelo ID.
     * <p>
     * Este método remove do sistema o usuário com o ID especificado. Caso o usuário não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id ID do usuário a ser deletado.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o usuário com o ID fornecido não for encontrado.
     */
    void deleteUsuario(Integer id);

    /**
     * Atualiza os dados de um usuário existente.
     * <p>
     * Este método atualiza os dados de um usuário pelo ID. Caso o usuário não seja encontrado,
     * uma exceção será lançada.
     * </p>
     *
     * @param id      ID do usuário a ser atualizado.
     * @param usuario Entidade Usuario contendo os dados atualizados.
     * @throws ResponseStatusException (HttpStatus.NOT_FOUND) Se o usuário com o ID fornecido não for encontrado.
     */
    void updateUsuario(Integer id, Usuario usuario);
}
