package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.*;
import prati.projeto.redeSocial.repository.ComentarioForumRepository;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.PostForumRepository;
import prati.projeto.redeSocial.repository.RespostaForumRepository;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.service.PostForumService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostForumServiceImpl implements PostForumService {

    private final PostForumRepository postForumRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final ComentarioForumRepository comentarioForumRepository;
    private final RespostaForumRepository respostaForumRepository;

    @Override
    @Transactional
    public PostForumResponseDTO criarPost(PostForumRequestDTO dto) {
        Perfil perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado!"));

        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado!"));

        PostForum post = new PostForum();
        post.setTexto(dto.getTexto());
        post.setTags(dto.getTags());
        post.setPossuiSpoiler(dto.getPossuiSpoiler());
        post.setPerfil(perfil);
        post.setLivro(livro);
        post.setComentarios(new ArrayList<>());

        postForumRepository.save(post);
        return converterParaDTO(post);
    }

    @Override
    public Page<PostForumResponseDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostForum> postsPage = postForumRepository.findAll(pageable);

        return postsPage.map(post -> {
            PostForumResponseDTO dto = converterParaDTO(post);

            Pageable comentariosPageable = PageRequest.of(0, 10);
            Page<ComentarioForum> comentariosPage = comentarioForumRepository.findByPostForumId(post.getId(), comentariosPageable);

            List<ComentarioForumResponseDTO> comentariosDTO = comentariosPage.getContent().stream()
                    .map(this::converterComentarioParaDTO)
                    .collect(Collectors.toList());
            dto.setComentarios(comentariosDTO);

            return dto;
        });
    }

    @Override
    public PostForumResponseDTO buscarPorId(Integer id) {
        PostForum post = postForumRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Post não encontrado!"));

        Pageable comentariosPageable = PageRequest.of(0, 10);
        Page<ComentarioForum> comentariosPage = comentarioForumRepository.findByPostForumId(post.getId(), comentariosPageable);

        PostForumResponseDTO dto = converterParaDTO(post);
        List<ComentarioForumResponseDTO> comentariosDTO = comentariosPage.getContent().stream()
                .map(this::converterComentarioParaDTO)
                .collect(Collectors.toList());
        dto.setComentarios(comentariosDTO);

        return dto;
    }

    @Transactional
    @Override
    public void deletarPost(Integer id) {
        if (!postForumRepository.existsById(id)) {
            throw new RegraNegocioException("Post não encontrado para exclusão!");
        }
        postForumRepository.deleteById(id);
    }

    private PostForumResponseDTO converterParaDTO(PostForum post) {
        PostForumResponseDTO dto = new PostForumResponseDTO();
        dto.setId(post.getId());
        dto.setTexto(post.getTexto());
        dto.setTags(post.getTags());
        dto.setPossuiSpoiler(post.getPossuiSpoiler());
        dto.setNomePerfil(post.getPerfil().getUsuario().getUsername());
        dto.setTituloLivro(post.getLivro().getTitulo());
        dto.setDataCriacao(post.getDataCriacao());

        List<ComentarioForumResponseDTO> comentariosDTO = post.getComentarios().stream()
                .map(this::converterComentarioParaDTO)
                .collect(Collectors.toList());
        dto.setComentarios(comentariosDTO);

        return dto;
    }

    private ComentarioForumResponseDTO converterComentarioParaDTO(ComentarioForum comentario) {
        ComentarioForumResponseDTO dto = new ComentarioForumResponseDTO();
        dto.setId(comentario.getId());
        dto.setNomePerfil(comentario.getPerfil().getUsuario().getUsername());
        dto.setTexto(comentario.getTexto());
        dto.setData(comentario.getData());

        Pageable respostasPageable = PageRequest.of(0, 10);
        Page<RespostaForum> respostasPage = respostaForumRepository.findByComentarioForumId(comentario.getId(), respostasPageable);

        List<RespostaForumResponseDTO> respostasDTO = respostasPage.getContent().stream()
                .map(this::converterRespostaParaDTO)
                .collect(Collectors.toList());
        dto.setRespostas(respostasDTO);

        return dto;
    }

    private RespostaForumResponseDTO converterRespostaParaDTO(RespostaForum resposta) {
        RespostaForumResponseDTO dto = new RespostaForumResponseDTO();
        dto.setId(resposta.getId());
        dto.setTexto(resposta.getTexto());
        dto.setNomePerfil(resposta.getPerfil().getUsuario().getUsername());
        dto.setData(resposta.getData());
        return dto;
    }
}