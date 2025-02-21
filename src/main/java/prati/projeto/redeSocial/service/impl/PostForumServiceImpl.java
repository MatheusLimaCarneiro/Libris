package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.PostForum;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.PostForumRepository;
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;
import prati.projeto.redeSocial.service.PostForumService;


@Service
@RequiredArgsConstructor
public class PostForumServiceImpl implements PostForumService {

    private final PostForumRepository postForumRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;

    @Transactional
    @Override
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

        postForumRepository.save(post);
        return converterParaDTO(post);
    }

    @Override
    public Page<PostForumResponseDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostForum> postsPage = postForumRepository.findAll(pageable);

        return postsPage.map(this::converterParaDTO);
    }

    @Override
    public PostForumResponseDTO buscarPorId(Integer id) {
        PostForum post = postForumRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Post não encontrado!"));
        return converterParaDTO(post);
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
        return dto;
    }
}