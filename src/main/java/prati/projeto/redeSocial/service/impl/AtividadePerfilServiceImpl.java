package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.AtividadePerfil;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.repository.AtividadePerfilRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.service.AtividadePerfilService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtividadePerfilServiceImpl implements AtividadePerfilService {

    private final AtividadePerfilRepository atividadePerfilRepository;
    private final PerfilRepository perfilRepository;

    @Override
    @Transactional
    public void registrarAtividade(Perfil perfil) {
        Perfil perfilValidado = buscarPerfilPorId(perfil.getId());

        LocalDate hoje = LocalDate.now();
        AtividadePerfil atividade = atividadePerfilRepository.findByPerfilAndData(perfilValidado, hoje);

        if (atividade != null) {
            atividade.setContador(atividade.getContador() + 1);
            atividadePerfilRepository.save(atividade);
        } else {
            AtividadePerfil novaAtividade = new AtividadePerfil();
            novaAtividade.setPerfil(perfilValidado);
            novaAtividade.setData(hoje);
            novaAtividade.setContador(1);
            atividadePerfilRepository.save(novaAtividade);
        }
    }

    @Override
    public List<AtividadePerfilDTO> consultarUltimasAtividades(Integer perfilId, int dias) {
        Perfil perfil = buscarPerfilPorId(perfilId);
        LocalDate dataReferencia = LocalDate.now().minusDays(dias);

        List<AtividadePerfil> atividades = atividadePerfilRepository.findByPerfilAndDataAfter(perfil, dataReferencia);

        return atividades.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AtividadePerfilDTO> listarAtividadesPaginadas(Integer perfilId, int page, int size, int dias) {
        Perfil perfil = buscarPerfilPorId(perfilId);
        LocalDate dataReferencia = LocalDate.now().minusDays(dias);

        Pageable pageable = PageRequest.of(page, size);
        Page<AtividadePerfil> atividadesPage = atividadePerfilRepository.findByPerfilIdAndDataAfter(perfil.getId(), dataReferencia, pageable);

        return atividadesPage.map(this::convertToDTO);
    }

    private AtividadePerfilDTO convertToDTO(AtividadePerfil atividade) {
        AtividadePerfilDTO dto = new AtividadePerfilDTO();
        dto.setData(atividade.getData());
        dto.setQuantidade(atividade.getContador());
        dto.setUsername(atividade.getPerfil().getUsuario().getUsername());
        return dto;
    }

    private Perfil buscarPerfilPorId(Integer perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil com ID " + perfilId + " não encontrado."));
    }
}