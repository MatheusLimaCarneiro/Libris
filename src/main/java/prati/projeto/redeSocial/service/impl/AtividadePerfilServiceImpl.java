package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.modal.entity.AtividadePerfil;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.repository.AtividadePerfilRepository;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.service.AtividadePerfilService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadePerfilServiceImpl implements AtividadePerfilService {

    private final AtividadePerfilRepository repository;

    public AtividadePerfilServiceImpl(AtividadePerfilRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void registrarAtividade(Perfil perfil) {
        LocalDate hoje = LocalDate.now();
        AtividadePerfil atividade = repository.findByPerfilAndData(perfil, hoje);

        if (atividade != null) {
            atividade.setContador(atividade.getContador() + 1);
            repository.save(atividade);
        } else {
            AtividadePerfil novaAtividade = new AtividadePerfil();
            novaAtividade.setPerfil(perfil);
            novaAtividade.setData(hoje);
            novaAtividade.setContador(1);
            repository.save(novaAtividade);
        }
    }

    @Override
    public List<AtividadePerfilDTO> consultarUltimasAtividades(PerfilDTO perfil, int dias) {
        LocalDate inicio = LocalDate.now().minusDays(dias);
        List<AtividadePerfil> atividades = repository.findByPerfilAndDataAfter(perfil, inicio);

        return atividades.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AtividadePerfilDTO convertToDTO(AtividadePerfil atividade) {
        AtividadePerfilDTO dto = new AtividadePerfilDTO();
        dto.setData(atividade.getData());
        dto.setQuantidade(atividade.getContador());
        return dto;
    }

}
