package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;

import java.util.List;

public interface AtividadePerfilService {

    void registrarAtividade(Perfil perfil);
    List<AtividadePerfilDTO> consultarUltimasAtividades(PerfilDTO perfil, int dias);
}
