package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Avaliacao;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Resenha;
import prati.projeto.redeSocial.repository.AvaliacaoRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.ResenhaRepository;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.service.AvaliacaoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoServiceImpl implements AvaliacaoService{

    private final AvaliacaoRepository avaliacaoRepository;
    private final ResenhaRepository resenhaRepository;
    private final PerfilRepository perfilRepository;

    @Override
    @Transactional
    public AvaliacaoDTO adicionarAvaliacao(Integer resenhaId, AvaliacaoDTO avaliacaoDTO) {
        if (avaliacaoRepository.existsByPerfilIdAndResenhaId(avaliacaoDTO.getPerfilId(), resenhaId)) {
            throw new RegraNegocioException("Este perfil já avaliou esta resenha");
        }

        Perfil perfil = buscarPerfilPorId(avaliacaoDTO.getPerfilId());
        Resenha resenha = buscarResenhaPorId(resenhaId);


        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setPerfil(perfil);
        avaliacao.setResenha(resenha);
        avaliacao.setTexto(avaliacaoDTO.getTexto());
        avaliacao.setNota(avaliacaoDTO.getNota());

        return convertToDTO(avaliacaoRepository.save(avaliacao));
    }


    @Override
    public AvaliacaoDTO editarAvaliacao(Integer avaliacaoId, AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = buscarAvaliacaoPorId(avaliacaoId);

        avaliacao.setTexto(avaliacaoDTO.getTexto());
        avaliacao.setNota(avaliacaoDTO.getNota());
        return convertToDTO(avaliacaoRepository.save(avaliacao));
    }

    @Override
    @Transactional
    public void deletarAvaliacao(Integer avaliacaoId) {
        buscarAvaliacaoPorId(avaliacaoId);
        avaliacaoRepository.deleteById(avaliacaoId);
    }

    @Override
    public List<AvaliacaoDTO> listarAvaliacoesPorPerfil(Integer perfilId) {
        validarExistenciaDePerfil(perfilId);

        return avaliacaoRepository.findByPerfilId(perfilId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<AvaliacaoDTO> listarAvaliacaoPorResenha(Integer resenhaId) {
        buscarResenhaPorId(resenhaId);

        return avaliacaoRepository.findByResenhaId(resenhaId).stream()
                .map(this::convertToDTO)
                .toList();
    }

    private void validarExistenciaDePerfil(Integer perfilId) {
        if (!perfilRepository.existsById(perfilId)) {
            throw new RegraNegocioException("Perfil com ID " + perfilId + " não encontrado.");
        }
    }

    private Perfil buscarPerfilPorId(Integer perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil com ID " + perfilId + " não encontrado."));
    }

    private Resenha buscarResenhaPorId(Integer resenhaId) {
        return resenhaRepository.findById(resenhaId)
                .orElseThrow(() -> new RegraNegocioException("Resenha com ID " + resenhaId + " não encontrada."));
    }

    private Avaliacao buscarAvaliacaoPorId(Integer avaliacaoId) {
        return avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new RegraNegocioException("Avaliação com ID " + avaliacaoId + " não encontrada."));
    }

    private AvaliacaoDTO convertToDTO(Avaliacao avaliacao) {
        return new AvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getPerfil().getId(),
                avaliacao.getTexto(),
                avaliacao.getNota()
        );
    }
}
