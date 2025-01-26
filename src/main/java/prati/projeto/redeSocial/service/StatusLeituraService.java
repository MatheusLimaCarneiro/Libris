package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;

public interface StatusLeituraService {

    /**
     * Salva o status de leitura de um livro para um perfil.
     * <p>
     * Este método recebe os dados necessários para associar um perfil a um livro com um status de leitura específico.
     * Após a validação dos dados, o status de leitura é salvo no banco de dados.
     * </p>
     *
     * @param perfilId      ID do perfil que está lendo o livro.
     * @param livroId       ID do livro para o qual o status de leitura será atribuído.
     * @param statusLeituraEnum O status de leitura do livro (LIDO, PAUSADO, LENDO, ABANDONADO).
     * @return O DTO StatusLeituraDTO com os dados persistidos do status de leitura, incluindo o ID, ID do perfil, ID do livro e o status.
     * @throws RuntimeException Se o perfil ou livro não forem encontrados.
     */
    StatusLeituraDTO salvarStatus(Integer perfilId, Integer livroId, StatusLeituraEnum statusLeituraEnum);

    /**
     * Atualiza o status de leitura de um livro para um perfil existente.
     * <p>
     * Este método permite alterar o status de leitura de um livro associado a um perfil.
     * Após a modificação do status, o novo valor é persistido no banco de dados.
     * </p>
     *
     * @param id           ID do status de leitura a ser atualizado.
     * @param novoStatus   O novo status de leitura para o livro (LIDO, PAUSADO, LENDO, ABANDONADO).
     * @return O DTO StatusLeituraDTO com os dados atualizados do status de leitura, incluindo o ID, ID do perfil, ID do livro e o novo status.
     * @throws RuntimeException Se o status de leitura não for encontrado.
     */
    StatusLeituraDTO mudarStatus(Integer id, StatusLeituraEnum novoStatus);

}
