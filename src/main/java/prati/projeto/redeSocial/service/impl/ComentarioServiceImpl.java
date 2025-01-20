package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.*;
import prati.projeto.redeSocial.repository.*;
import prati.projeto.redeSocial.rest.dto.*;
import prati.projeto.redeSocial.service.ComentarioRespostaService;
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
    private final ComentarioRespostaService respostaService;

    @Override
    @Transactional
    public Comentario salvar(ComentarioDTO dto) {
        Usuario usuario = validarUsuario(dto.getUsuarioEmail());
        Livro livro = validarLivro(dto.getLivroId());
        validarNota(dto.getNota());

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setUsuario(usuario);
        comentario.setLivro(livro);
        comentario.setNota(dto.getNota());

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
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        ComentarioDTO dto = convertToDTO(comentario);
        dto.setRespostas(respostaService.listarRespostasPorComentario(id));
        return dto;
    }

    @Override
    @Transactional
    public ComentarioDTO atualizarComentario(Integer id, ComentarioDTO dto) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        comentario.setTexto(dto.getTexto());
        comentario.setNota(dto.getNota());
        validarNota(dto.getNota());
        comentario.setDataComentario(LocalDateTime.now());

        comentario = comentarioRepository.save(comentario);
        return convertToDTO(comentario);
    }

    private Usuario validarUsuario(String usuarioEmail) {
        return usuarioRepository.findById(usuarioEmail)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));
    }

    private Livro validarLivro(Integer livroId) {
        return livroRepository.findById(livroId)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado"));
    }

    private void validarNota(Double nota) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new RegraNegocioException("A nota deve ser um valor entre 1 e 5");
        }
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setUsuarioEmail(comentario.getUsuario().getEmail());
        dto.setLivroId(comentario.getLivro().getId());
        dto.setTexto(comentario.getTexto());
        dto.setNota(comentario.getNota());
        dto.setDataComentario(comentario.getDataComentario());

        dto.setRespostas(convertRespostasToDTO(comentario.getRespostas()));
        return dto;
    }

    private List<RespostaDTO> convertRespostasToDTO(List<ComentarioResposta> respostas) {
        return respostas.stream()
                .map(this::convertRespostaToDTO)
                .collect(Collectors.toList());
    }

    private RespostaDTO convertRespostaToDTO(ComentarioResposta resposta) {
        return new RespostaDTO(
                resposta.getId(),
                resposta.getUsuario().getEmail(),
                resposta.getTexto(),
                resposta.getDataResposta()
        );
    }
}