package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prati.projeto.redeSocial.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.dto.ComentarioDTO;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Avaliacao;
import prati.projeto.redeSocial.modal.entity.Comentario;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.AvaliacaoRepository;
import prati.projeto.redeSocial.repository.ComentarioRepository;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.service.ComentarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Override
    @Transactional
    public Comentario salvar(ComentarioDTO dto) {
        Usuario usuario = validarUsuario(dto.getUsuarioId());
        Livro livro = validarLivro(dto.getLivroId());
        Avaliacao avaliacao = converterAvaliacao(dto.getAvaliacao(),livro);

        Comentario comentario = criarComentario(dto, usuario, livro, avaliacao);

        avaliacaoRepository.save(avaliacao);
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<ComentarioDTO> listarTodos() {
        return comentarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO buscarPorId(Integer id) {
        return comentarioRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }

    private Usuario validarUsuario(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraNegocioException("Código de Usuário inválido"));
    }

    private Livro validarLivro(Integer livroId) {
        return livroRepository.findById(livroId)
                .orElseThrow(() -> new RegraNegocioException("Código de Livro inválido"));
    }

    private Avaliacao converterAvaliacao(AvaliacaoDTO avaliacaoDTO, Livro livroId) {
        if (avaliacaoDTO == null) {
            throw new RegraNegocioException("Não é possível realizar um comentário sem avaliação.");
        }
        validarNotaAvaliacao(avaliacaoDTO.getNota());

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setLivro(livroId);
        avaliacao.setNota(avaliacaoDTO.getNota());
        return avaliacaoRepository.save(avaliacao);
    }

    private void validarNotaAvaliacao(Double nota) {
        if (nota == null || nota < 0 || nota > 5) {
            throw new RegraNegocioException("A nota deve ser um valor entre 0 e 5.");
        }
    }

    private Comentario criarComentario(ComentarioDTO dto, Usuario usuario, Livro livro, Avaliacao avaliacao) {
        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setUsuario(usuario);
        comentario.setLivro(livro);
        comentario.setAvaliacao(avaliacao);
        return comentario;
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setUsuarioId(comentario.getUsuario().getId());
        dto.setLivroId(comentario.getLivro().getId());
        dto.setTexto(comentario.getTexto());
        dto.setDataComentario(comentario.getDataComentario());

        if (comentario.getAvaliacao() != null) {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
            avaliacaoDTO.setNota(comentario.getAvaliacao().getNota());
            dto.setAvaliacao(avaliacaoDTO);
        }

        return dto;
    }
}
