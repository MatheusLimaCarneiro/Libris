package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;

public interface StatusLeituraService {

    /**
     * Salva o status de leitura de um livro para um perfil.
     * <p>
     * Este método permite associar um perfil a um livro, atribuindo a ele um status de leitura específico, como LIDO, PAUSADO, LENDO ou ABANDONADO.
     * Caso o perfil já tenha um status associado ao livro, uma exceção será lançada.
     * </p>
     *
     * @param perfilId      ID do perfil que está lendo o livro.
     * @param livroId       ID do livro para o qual o status de leitura será atribuído.
     * @param statusLeituraEnum O status de leitura do livro (LIDO, PAUSADO, LENDO, ABANDONADO).
     * @param pagina        A página atual do livro que o perfil está lendo.
     * @return O DTO StatusLeituraDTO com os dados persistidos do status de leitura, incluindo o ID, ID do perfil, ID do livro e o status.
     * @throws RegraNegocioException Se o perfil ou livro não forem encontrados, ou se o perfil já tiver um status para o livro.
     * @throws RegraNegocioException Se a página fornecida for inválida (menor que 1 ou maior que o número de páginas do livro).
     */
    StatusLeituraDTO salvarStatus(Integer perfilId, Integer livroId, StatusLeituraEnum statusLeituraEnum, Integer pagina);

    /**
     * Atualiza o status de leitura de um livro para um perfil existente.
     * <p>
     * Este método permite alterar o status de leitura de um livro que já está associado a um perfil.
     * A página e o novo status serão atualizados na entidade StatusLeitura.
     * </p>
     *
     * @param id           ID do status de leitura a ser atualizado.
     * @param pagina       A página atual do livro que o perfil está lendo.
     * @param novoStatus   O novo status de leitura para o livro (LIDO, PAUSADO, LENDO, ABANDONADO).
     * @return O DTO StatusLeituraDTO com os dados atualizados do status de leitura, incluindo o ID, ID do perfil, ID do livro, a página e o novo status.
     * @throws RegraNegocioException Se o status de leitura não for encontrado.
     * @throws RegraNegocioException Se a página fornecida for inválida (menor que 1 ou maior que o número de páginas do livro).
     */
    StatusLeituraDTO mudarStatus(Integer id, Integer pagina, StatusLeituraEnum novoStatus);

    /**
     * Lista os status de leitura de livros para os perfis.
     * <p>
     * Este método retorna uma lista paginada de todos os status de leitura registrados no sistema.
     * </p>
     *
     * @param page Número da página para paginar a lista de status de leitura.
     * @param size Número de elementos por página.
     * @return Uma página de objetos {@link StatusLeituraDTO}, com os dados dos status de leitura, incluindo o ID, perfil, livro, página e status.
     */
    Page<StatusLeituraDTO> listarStatus(int page, int size);

}
