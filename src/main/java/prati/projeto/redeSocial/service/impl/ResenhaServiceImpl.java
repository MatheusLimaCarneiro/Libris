package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import prati.projeto.redeSocial.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.dto.ResenhaDTO;
import prati.projeto.redeSocial.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Resenha;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.ResenhaRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResenhaServiceImpl implements ResenhaService {

    private final ResenhaRepository resenhaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    @Override
    public ResenhaViewDTO getResenhaById(Integer id) {
        Resenha resenha = resenhaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resenha com ID " + id + " não encontrada"));
        return convertToViewDTO(resenha);
    }

    @Override
    public Integer saveResenha(ResenhaDTO resenhaDTO) {
        Usuario usuario = validarUsuario(resenhaDTO.getUsuarioId());
        Livro livro = validarLivro(resenhaDTO.getLivroId());

        if (resenhaDTO.getNota() < 0 || resenhaDTO.getNota() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota deve estar entre 0 e 5");
        }

        Resenha resenha = criarResenha(resenhaDTO, usuario, livro);
        resenhaRepository.save(resenha);
        return resenha.getId();
    }

    @Override
    public void deleteResenha(Integer id) {
        resenhaRepository.findById(id)
                .ifPresentOrElse(
                        resenhaRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resenha não encontrada"); }
                );
    }

    @Override
    public void updateResenha(Integer id, ResenhaDTO resenhaDTO) {
        Resenha resenhaExistente = resenhaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resenha não encontrada"));

        Usuario usuario = validarUsuario(resenhaDTO.getUsuarioId());
        Livro livro = validarLivro(resenhaDTO.getLivroId());

        resenhaExistente.setUsuario(usuario);
        resenhaExistente.setLivro(livro);
        resenhaExistente.setTitulo(resenhaDTO.getTitulo());
        resenhaExistente.setAutor(resenhaDTO.getAutor());
        resenhaExistente.setNota(resenhaDTO.getNota());
        resenhaExistente.setDataEdicao(LocalDateTime.now());

        resenhaRepository.save(resenhaExistente);
    }

    @Override
    public List<ResenhaViewDTO> findByLivro(Integer livroId) {
        validarLivro(livroId);
        return resenhaRepository.findByLivroId(livroId).stream()
                .map(this::convertToViewDTO)
                .collect(Collectors.toList());
    }

    private Usuario validarUsuario(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
    }

    private Livro validarLivro(Integer livroId) {
        return livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro não encontrado"));
    }

    private Resenha criarResenha(ResenhaDTO dto, Usuario usuario, Livro livro) {
        Resenha resenha = new Resenha();
        resenha.setUsuario(usuario);
        resenha.setLivro(livro);
        resenha.setTitulo(dto.getTitulo());
        resenha.setAutor(dto.getAutor());
        resenha.setNota(dto.getNota());
        resenha.setDataPublicacao(LocalDateTime.now());
        resenha.setDataEdicao(LocalDateTime.now());
        return resenha;
    }

    private ResenhaViewDTO convertToViewDTO(Resenha resenha) {
        return new ResenhaViewDTO(
                resenha.getId(),
                new UsuarioResumidoDTO(resenha.getUsuario().getUsername(), resenha.getUsuario().getEmail()),
                new LivroResumidoDTO(
                        resenha.getLivro().getTitulo(),
                        resenha.getLivro().getAutor(),
                        resenha.getLivro().getDataPublicacao().toString()
                ),
                resenha.getTitulo(),
                resenha.getAutor(),
                resenha.getDataPublicacao().toString(),
                resenha.getDataEdicao().toString(),
                resenha.getNota()
        );
    }
}

