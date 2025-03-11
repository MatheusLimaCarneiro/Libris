package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;

import java.util.List;

public interface AtividadePerfilService {

    void registrarAtividade(Perfil perfil);
    List<AtividadePerfilDTO> consultarUltimasAtividades(Integer perfilId, int dias);
    Page<AtividadePerfilDTO> listarAtividadesPaginadas(Integer perfilId, int page, int size, int dias);
}
