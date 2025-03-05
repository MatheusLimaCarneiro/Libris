package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Resenha;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.ResenhaRepository;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenhaServiceImpl implements ResenhaService {

    private final ResenhaRepository resenhaRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final AvaliacaoServiceImpl avaliacaoService;

    @Override
    public ResenhaViewDTO getResenhaById(Integer id) {
        Resenha resenha = buscarResenhaPorId(id);
        return converterParaViewDTO(resenha, 0, 10);
    }

    @Override
    @Transactional
    public ResenhaViewDTO saveResenha(ResenhaDTO resenhaDTO) {
        validarNota(resenhaDTO.getNota());

        Perfil perfil = buscarPerfilPorId(resenhaDTO.getPerfilId());
        Livro livro = buscarLivroPorGoogleId(resenhaDTO.getGoogleId());

        validarResenhaExistente(perfil.getId(), livro.getId());

        Resenha resenha = criarResenha(resenhaDTO, perfil, livro);
        resenhaRepository.save(resenha);

        return converterParaViewDTO(resenha, 0, 10);
    }

    @Override
    @Transactional
    public void deleteResenha(Integer id) {
        Resenha resenha = buscarResenhaPorId(id);
        resenha.getAvaliacoes().clear();
        resenhaRepository.delete(resenha);
    }

    @Override
    @Transactional
    public void updateResenha(Integer id, ResenhaDTO resenhaDTO) {
        Resenha resenhaExistente = buscarResenhaPorId(id);
        Perfil perfil = buscarPerfilPorId(resenhaDTO.getPerfilId());
        Livro livro = buscarLivroPorGoogleId(resenhaDTO.getGoogleId());

        atualizarResenhaExistente(resenhaExistente, resenhaDTO, perfil, livro);
        resenhaRepository.save(resenhaExistente);
    }

    @Override
    public Page<ResenhaViewDTO> findByGoogleId(String googleId, int page, int size) {

        buscarLivroPorGoogleId(googleId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Resenha> resenhasPage = resenhaRepository.findByGoogleIdLivro(googleId, pageable);
        return resenhasPage.map(resenha -> converterParaViewDTO(resenha, 0, 10));
    }

    @Override
    public Page<ResenhaViewDTO> findAllResenhas(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Resenha> resenhasPage = resenhaRepository.findAll(pageable);
        return resenhasPage.map(resenha -> converterParaViewDTO(resenha, 0, 10));
    }


    private Resenha buscarResenhaPorId(Integer id) {
        return resenhaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Resenha com ID " + id + " não encontrada."));
    }

    private Perfil buscarPerfilPorId(Integer perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil com ID " + perfilId + " não encontrado."));
    }

    private Livro buscarLivroPorGoogleId(String googleId) {
        return livroRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new RegraNegocioException("Livro com Google ID " + googleId + " não encontrado."));
    }

    private void validarNota(Double nota) {
        if (nota == null || nota < 0 || nota > 5) {
            throw new RegraNegocioException("A nota deve ser um valor entre 0 e 5.");
        }
    }

    private void validarResenhaExistente(Integer perfilId, Integer livroId) {
        if (resenhaRepository.findByPerfilIdAndLivroId(perfilId, livroId) != null) {
            throw new RegraNegocioException("O perfil já possui uma resenha para este livro.");
        }
    }

    private Resenha criarResenha(ResenhaDTO dto, Perfil perfil, Livro livro) {
        Resenha resenha = new Resenha();
        resenha.setPerfil(perfil);
        resenha.setLivro(livro);
        resenha.setGoogleIdLivro(dto.getGoogleId());
        resenha.setTexto(dto.getTexto());
        resenha.setTitulo(dto.getTitulo());
        resenha.setAutor(dto.getAutor());
        resenha.setNota(dto.getNota());
        resenha.setDataPublicacao(LocalDateTime.now());
        resenha.setDataEdicao(LocalDateTime.now());
        resenha.setSpoiler(dto.isSpoiler());
        return resenha;
    }

    private void atualizarResenhaExistente(Resenha resenha, ResenhaDTO dto, Perfil perfil, Livro livro) {
        resenha.setPerfil(perfil);
        resenha.setLivro(livro);
        resenha.setGoogleIdLivro(dto.getGoogleId());
        resenha.setTexto(dto.getTexto());
        resenha.setTitulo(dto.getTitulo());
        resenha.setAutor(dto.getAutor());
        resenha.setNota(dto.getNota());
        resenha.setDataEdicao(LocalDateTime.now());
    }

    private ResenhaViewDTO converterParaViewDTO(Resenha resenha, int page, int size) {
        Page<AvaliacaoDTO> avaliacoesPage = avaliacaoService.listarAvaliacaoPorResenha(resenha.getId(), page, size);
        List<AvaliacaoDTO> avaliacoesDTO = avaliacoesPage.getContent();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataPublicacaoFormatada = resenha.getDataPublicacao().format(formatter);
        String dataEdicaoFormatada = resenha.getDataEdicao().format(formatter);

        return new ResenhaViewDTO(
                resenha.getId(),
                resenha.getPerfil().getId(),
                new LivroResumidoDTO(
                        resenha.getLivro().getTitulo(),
                        resenha.getLivro().getAutores(),
                        resenha.getLivro().getDataPublicacao().toString()
                ),
                resenha.getGoogleIdLivro(),
                resenha.getTitulo(),
                resenha.getAutor(),
                resenha.getTexto(),
                dataPublicacaoFormatada,
                dataEdicaoFormatada,
                resenha.getNota(),
                resenha.isSpoiler(),
                resenha.getMedia(),
                avaliacoesDTO
        );
    }
}