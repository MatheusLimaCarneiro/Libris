package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.*;
import prati.projeto.redeSocial.repository.*;
import prati.projeto.redeSocial.rest.dto.*;
import prati.projeto.redeSocial.service.ComentarioRespostaService;
import prati.projeto.redeSocial.service.ComentarioService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final ComentarioRespostaService respostaService;


    @Override
    @Transactional
    public ComentarioDTO salvar(ComentarioDTO dto) {
        Perfil perfil = validarPerfil(dto.getPerfilId());
        Livro livro = validarLivro(dto.getLivroId());
        validarNota(dto.getNota());

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setPerfil(perfil);
        comentario.setLivro(livro);
        comentario.setNota(dto.getNota());
        comentario.setRespostas(new ArrayList<>());

        comentario = comentarioRepository.save(comentario);
        return convertToDTO(comentario);
    }

    @Override
    public Page<ComentarioDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> comentariosPage = comentarioRepository.findAll(pageable);

        return comentariosPage.map(comentario -> {
            ComentarioDTO dto = convertToDTO(comentario);

            Page<RespostaDTO> respostasPage = respostaService.listarRespostasPorComentario(comentario.getId(), 0, 10);
            dto.setRespostas(respostasPage.getContent());

            return dto;
        });
    }

    @Override
    public ComentarioDTO buscarPorId(Integer id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        ComentarioDTO dto = convertToDTO(comentario);

        Page<RespostaDTO> respostasPage = respostaService.listarRespostasPorComentario(id, 0, 10);
        dto.setRespostas(respostasPage.getContent());

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

    @Override
    @Transactional
    public void excluirComentario(Integer id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        comentarioRepository.delete(comentario);
    }

    @Override
    public Page<ComentarioDTO> listarPorLivro(Integer livroId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> comentariosPage = comentarioRepository.findByLivroId(livroId, pageable);

        return comentariosPage.map(comentario -> {
            ComentarioDTO dto = convertToDTO(comentario);

            Page<RespostaDTO> respostasPage = respostaService.listarRespostasPorComentario(comentario.getId(), 0, 10);
            dto.setRespostas(respostasPage.getContent());

            return dto;
        });
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
        if (nota == null || nota < 1 || nota > 5) {
            throw new RegraNegocioException("A nota deve ser um valor entre 1 e 5");
        }
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setPerfilId(comentario.getPerfil().getId());
        dto.setLivroId(comentario.getLivro().getId());
        dto.setTexto(comentario.getTexto());
        dto.setNota(comentario.getNota());
        dto.setDataComentario(comentario.getDataComentario());

        return dto;
    }
}