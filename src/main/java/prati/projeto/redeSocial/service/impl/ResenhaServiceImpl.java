package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Resenha;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.ResenhaRepository;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
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
        Resenha resenha = resenhaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Resenha com ID " + id + " não encontrada"));
        return convertToViewDTO(resenha, 0, 10);
    }

    @Override
    @Transactional
    public Integer saveResenha(ResenhaDTO resenhaDTO) {
        Livro livro = validarLivro(resenhaDTO.getLivroId());
        Perfil perfil = validarPerfil(resenhaDTO.getPerfilId());
        validarNota(resenhaDTO.getNota());

        Resenha resenhaExistente = resenhaRepository.findByPerfilIdAndLivroId(perfil.getId(), livro.getId());
        if (resenhaExistente != null) {
            throw new RegraNegocioException("O perfil já possui uma resenha para este livro.");
        }

        Resenha resenha = criarResenha(resenhaDTO, perfil, livro);
        resenhaRepository.save(resenha);
        return resenha.getId();
    }

    @Override
    @Transactional
    public void deleteResenha(Integer id) {
        Resenha resenha = resenhaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Resenha não encontrada"));

        resenha.getAvaliacoes().clear();

        resenhaRepository.delete(resenha);
    }

    @Override
    @Transactional
    public void updateResenha(Integer id, ResenhaDTO resenhaDTO) {
        Resenha resenhaExistente = resenhaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Resenha não encontrada"));

        Perfil perfil = validarPerfil(resenhaDTO.getPerfilId());
        Livro livro = validarLivro(resenhaDTO.getLivroId());
        validarNota(resenhaDTO.getNota());

        resenhaExistente.setPerfil(perfil);
        resenhaExistente.setLivro(livro);
        resenhaExistente.setTitulo(resenhaDTO.getTitulo());
        resenhaExistente.setAutor(resenhaDTO.getAutor());
        resenhaExistente.setTexto(resenhaDTO.getTexto());
        resenhaExistente.setNota(resenhaDTO.getNota());
        resenhaExistente.setDataEdicao(LocalDateTime.now());

        resenhaRepository.save(resenhaExistente);
    }

    @Override
    public Page<ResenhaViewDTO> findByLivro(Integer livroId, int page, int size) {
        validarLivro(livroId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Resenha> resenhasPage = resenhaRepository.findByLivroId(livroId, pageable);
        return resenhasPage.map(resenha -> convertToViewDTO(resenha, 0, 10));
    }


    @Override
    public Page<ResenhaViewDTO> findAllResenhas(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Resenha> resenhasPage = resenhaRepository.findAll(pageable);
        return resenhasPage.map(resenha -> convertToViewDTO(resenha, 0, 10));
    }

    private Perfil validarPerfil(Integer perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
    }

    private Livro validarLivro(Integer livroId) {
        return livroRepository.findById(livroId)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado"));
    }

    private void validarNota(Double nota) {
        if (nota < 0 || nota > 5) {
            throw new RegraNegocioException("A nota deve estar entre 0 e 5");
        }
    }

    private Resenha criarResenha(ResenhaDTO dto, Perfil perfil, Livro livro) {
        Resenha resenha = new Resenha();
        resenha.setPerfil(perfil);
        resenha.setLivro(livro);
        resenha.setTexto(dto.getTexto());
        resenha.setTitulo(dto.getTitulo());
        resenha.setAutor(dto.getAutor());
        resenha.setNota(dto.getNota());
        resenha.setDataPublicacao(LocalDateTime.now());
        resenha.setDataEdicao(LocalDateTime.now());
        return resenha;
    }

    private ResenhaViewDTO convertToViewDTO(Resenha resenha, int page, int size) {
        Page<AvaliacaoDTO> avaliacoesPage = avaliacaoService.listarAvaliacaoPorResenha(resenha.getId(), page, size);

        List<AvaliacaoDTO> avaliacoesDTO = avaliacoesPage.getContent();

        return new ResenhaViewDTO(
                resenha.getId(),
                resenha.getPerfil().getId(),
                new LivroResumidoDTO(
                        resenha.getLivro().getTitulo(),
                        resenha.getLivro().getAutores(),
                        resenha.getLivro().getDataPublicacao().toString()
                ),
                resenha.getTitulo(),
                resenha.getAutor(),
                resenha.getTexto(),
                resenha.getDataPublicacao().toString(),
                resenha.getDataEdicao().toString(),
                resenha.getNota(),
                avaliacoesDTO
        );
    }
}

