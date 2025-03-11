package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;

import java.util.List;

public interface AtividadePerfilService {

    /**
     * Registra uma atividade para um perfil específico.
     * Se já existir uma atividade registrada para o perfil na data atual, o contador de atividades é incrementado.
     * Caso contrário, uma nova atividade é criada.
     *
     * @param perfil O perfil para o qual a atividade será registrada.
     */
    void registrarAtividade(Perfil perfil);

    /**
     * Consulta as últimas atividades de um perfil dentro de um período específico (em dias).
     * Retorna uma lista de atividades no formato DTO.
     *
     * @param perfilId O ID do perfil cujas atividades serão consultadas.
     * @param dias O número de dias para trás a partir da data atual.
     * @return Uma lista de {@link AtividadePerfilDTO} contendo as atividades recentes.
     */
    List<AtividadePerfilDTO> consultarUltimasAtividades(Integer perfilId, int dias);

    /**
     * Lista as atividades de um perfil de forma paginada, dentro de um período específico (em dias).
     * Retorna uma página de atividades no formato DTO.
     *
     * @param perfilId O ID do perfil cujas atividades serão listadas.
     * @param page O número da página a ser retornada.
     * @param size O número de atividades por página.
     * @param dias O número de dias para trás a partir da data atual.
     * @return Uma página de {@link AtividadePerfilDTO} contendo as atividades paginadas.
     */
    Page<AtividadePerfilDTO> listarAtividadesPaginadas(Integer perfilId, int page, int size, int dias);
}