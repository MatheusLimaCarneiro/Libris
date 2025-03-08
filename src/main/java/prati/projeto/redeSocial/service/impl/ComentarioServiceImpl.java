package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final ComentarioRespostaService respostaService;
    private final UsuarioRepository usuarioRepository;

    @Override
    @CacheEvict(value = "comentariosPorLivro", key = "{#dto.googleId, 0, 10}")
    @Transactional
    public ComentarioDTO salvar(ComentarioRequestDTO dto) {
        Perfil perfil = validarPerfil(dto.getPerfilId());
        Livro livro = livroRepository.findByGoogleId(dto.getGoogleId())
                .orElseThrow(() -> new RegraNegocioException("Livro com Google ID " + dto.getGoogleId() + " não encontrado"));

        validarNota(dto.getNota());

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setPerfil(perfil);
        comentario.setLivro(livro);
        comentario.setGoogleIdLivro(dto.getGoogleId());
        comentario.setNota(dto.getNota());
        comentario.setSpoiler(dto.isSpoiler());
        comentario.setRespostas(new ArrayList<>());

        comentario = comentarioRepository.save(comentario);
        return convertToDTO(comentario);
    }

    @Cacheable(value = "comentarios", key = "{#page, #size}")
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
    @CacheEvict(value = {"comentarios", "comentariosPorLivro", "comentariosPorUsername"}, key = "{#dto.googleId, 0, 10}")
    @Transactional
    public ComentarioDTO atualizarComentario(Integer id, ComentarioRequestDTO dto) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        Perfil perfil = validarPerfil(dto.getPerfilId());

        Livro livro = livroRepository.findByGoogleId(dto.getGoogleId())
                .orElseThrow(() -> new RegraNegocioException("Livro com Google ID " + dto.getGoogleId() + " não encontrado"));

        validarNota(dto.getNota());

        comentario.setTexto(dto.getTexto());
        comentario.setDataComentario(LocalDateTime.now());
        comentario.setPerfil(perfil);
        comentario.setLivro(livro);
        comentario.setGoogleIdLivro(dto.getGoogleId());
        comentario.setNota(dto.getNota());
        comentario.setSpoiler(dto.isSpoiler());

        comentario = comentarioRepository.save(comentario);
        return convertToDTO(comentario);
    }

    @Override
    @CacheEvict(value = {"comentarios", "comentariosPorLivro", "comentariosPorUsername"}, key = "{#comentario.googleIdLivro, 0, 10}")
    @Transactional
    public void excluirComentario(Integer id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Comentário não encontrado"));

        comentarioRepository.delete(comentario);
    }

    @Override
    @Cacheable(value = "comentariosPorLivro", key = "{#googleIdLivro, #page, #size}")
    public Page<ComentarioDTO> listarPorLivro(String googleIdLivro, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> comentariosPage = comentarioRepository.findByGoogleIdLivro(googleIdLivro, pageable);

        return comentariosPage.map(comentario -> {
            ComentarioDTO dto = convertToDTO(comentario);

            Page<RespostaDTO> respostasPage = respostaService.listarRespostasPorComentario(comentario.getId(), 0, 10);
            dto.setRespostas(respostasPage.getContent());

            return dto;
        });
    }

    @Override
    @Cacheable(value = "comentariosPorUsername", key = "{#username, #page, #size}")
    public Page<ComentarioDTO> listarComentariosPorUsername(String username, int page, int size) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado com o username: " + username));

        Perfil perfil = usuario.getPerfil();
        if (perfil == null) {
            throw new RegraNegocioException("Perfil não encontrado para o usuário: " + username);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Comentario> comentariosPage = comentarioRepository.findByPerfil(perfil, pageable);

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

    private void validarNota(Double nota) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new RegraNegocioException("A nota deve ser um valor entre 1 e 5");
        }
    }

    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(comentario.getId());
        dto.setPerfilId(comentario.getPerfil().getId());
        dto.setGoogleId(comentario.getGoogleIdLivro());
        dto.setTexto(comentario.getTexto());
        dto.setNota(comentario.getNota());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = comentario.getDataComentario().format(formatter);
        dto.setDataComentario(dataFormatada);

        dto.setQuantidadeCurtidas(comentario.getQuantidadeCurtidas());
        dto.setSpoiler(comentario.isSpoiler());
        return dto;
    }
}