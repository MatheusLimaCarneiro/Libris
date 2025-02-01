package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.RelacionamentoSeguidores;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.RelacionamentoSeguidoresRepository;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.service.RelacionamentoSeguidoresService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RelacionamentoSeguidoresServiceImpl implements RelacionamentoSeguidoresService {

    private final RelacionamentoSeguidoresRepository relacionamentoRepository;
    private final PerfilRepository perfilRepository;

    @Override
    @Transactional
    public void seguirPerfil(Integer seguidorId, Integer seguidoId) {
        validarSeguirPerfil(seguidorId, seguidoId);

        Perfil seguidor = buscarPerfilPorId(seguidorId, "Perfil seguidor não encontrado");
        Perfil seguido = buscarPerfilPorId(seguidoId, "Perfil a ser seguido não encontrado");

        RelacionamentoSeguidores relacionamento = new RelacionamentoSeguidores();
        relacionamento.setSeguidor(seguidor);
        relacionamento.setSeguido(seguido);
        relacionamentoRepository.save(relacionamento);

        atualizarContagemSeguidores(seguidor, seguido);
    }

    @Override
    @Transactional
    public void deixarDeSeguir(Integer seguidorId, Integer seguidoId) {
        relacionamentoRepository.findBySeguidorIdAndSeguidoId(seguidorId, seguidoId)
                .ifPresent(relacionamento -> {
                    relacionamentoRepository.delete(relacionamento);
                    atualizarContagemSeguidores(relacionamento.getSeguidor(), relacionamento.getSeguido());
                });
    }

    @Override
    public boolean estaSeguindo(Integer seguidorId, Integer seguidoId) {
        return relacionamentoRepository.findBySeguidorId(seguidorId).stream()
                .anyMatch(r -> r.getSeguido().getId().equals(seguidoId));
    }

    @Override
    public Set<PerfilResumidoDTO> buscarSeguidores(Integer perfilId) {
        buscarPerfilPorId(perfilId, "Perfil não encontrado");
        return relacionamentoRepository.findBySeguidoId(perfilId).stream()
                .map(relacionamento -> converterParaPerfilResumidoDTO(relacionamento.getSeguidor()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PerfilResumidoDTO> buscarSeguindo(Integer perfilId) {
        buscarPerfilPorId(perfilId, "Perfil não encontrado");
        return relacionamentoRepository.findBySeguidorId(perfilId).stream()
                .map(relacionamento -> converterParaPerfilResumidoDTO(relacionamento.getSeguido()))
                .collect(Collectors.toSet());
    }

    private void validarSeguirPerfil(Integer seguidorId, Integer seguidoId) {
        if (seguidorId.equals(seguidoId)) {
            throw new RegraNegocioException("Não é possível seguir seu próprio perfil");
        }
        if (estaSeguindo(seguidorId, seguidoId)) {
            throw new RegraNegocioException("Você já está seguindo este perfil");
        }
    }

    private Perfil buscarPerfilPorId(Integer perfilId, String mensagemErro) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException(mensagemErro));
    }

    private void atualizarContagemSeguidores(Perfil seguidor, Perfil seguido) {
        seguidor.setSeguindo(relacionamentoRepository.contarSeguindo(seguidor.getId()));
        seguido.setSeguidores(relacionamentoRepository.contarSeguidores(seguido.getId()));

        perfilRepository.save(seguidor);
        perfilRepository.save(seguido);
    }

    private PerfilResumidoDTO converterParaPerfilResumidoDTO(Perfil perfil) {
        return new PerfilResumidoDTO(
                perfil.getId(),
                perfil.getUrlPerfil(),
                perfil.getResumoBio(),
                perfil.getUsuario().getUsername()
        );
    }
}

