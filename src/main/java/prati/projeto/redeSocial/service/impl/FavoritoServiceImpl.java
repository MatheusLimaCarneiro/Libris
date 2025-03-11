package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Favoritos;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.FavoritoRepository;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.FavoritoRequestDTO;
import prati.projeto.redeSocial.rest.dto.FavoritoResponseDTO;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.service.FavoritoService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FavoritoServiceImpl implements FavoritoService{

    private final FavoritoRepository favoritoRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadePerfilServiceImpl atividadePerfilService;

    @Override
    public FavoritoResponseDTO getFavoritoById(Integer id) {
        Favoritos favorito = buscarFavoritoPorId(id);
        return converterParaDTO(favorito);
    }

    @Override
    @Transactional
    public FavoritoResponseDTO favoritarLivro(FavoritoRequestDTO dto) {
        Perfil perfil = buscarPerfilPorId(dto.getPerfilId());
        Livro livro = buscarLivroPorGoogleId(dto.getGoogleId());

        validarFavoritoExistente(perfil.getId(), livro.getId());

        if (favoritoRepository.existsByPerfilIdAndLivroId(perfil.getId(), livro.getId())) {
            throw new RegraNegocioException("Este livro já foi favoritado por este perfil.");
        }

        Favoritos favorito = criarFavorito(perfil, livro, dto.getGoogleId());
        favoritoRepository.save(favorito);
        //Rastreia atividade
        atividadePerfilService.registrarAtividade(favorito.getPerfil());

        return converterParaDTO(favorito);
    }

    @Override
    @Transactional
    public void desfavoritarLivro(Integer id) {
        Favoritos favorito = buscarFavoritoPorId(id);
        favoritoRepository.delete(favorito);
    }

    @Override
    public Page<FavoritoResponseDTO> listarFavoritosPorPerfil(Integer perfilId, int page, int size) {
        Perfil perfil = buscarPerfilPorId(perfilId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Favoritos> favoritosPage = favoritoRepository.findByPerfil(perfil, pageable);
        return favoritosPage.map(this::converterParaDTO);
    }

    @Override
    public Page<FavoritoResponseDTO> listarTodosFavoritos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Favoritos> favoritosPage = favoritoRepository.findAll(pageable);
        return favoritosPage.map(this::converterParaDTO);
    }

    @Override
    public Page<FavoritoResponseDTO> filtrarFavoritos(String username, String googleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (username == null && googleId == null) {
            return listarTodosFavoritos(page, size);
        }

        if (username != null && googleId != null) {
            Perfil perfil = buscarPerfilPorUsername(username);
            Livro livro = buscarLivroPorGoogleId(googleId);
            return favoritoRepository.findByPerfilAndLivro(perfil, livro, pageable)
                    .map(this::converterParaDTO);
        }

        if (username != null) {
            Perfil perfil = buscarPerfilPorUsername(username);
            return favoritoRepository.findByPerfil(perfil, pageable)
                    .map(this::converterParaDTO);
        }

        Livro livro = buscarLivroPorGoogleId(googleId);
        return favoritoRepository.findByLivro(livro, pageable)
                .map(this::converterParaDTO);
    }

    private Favoritos buscarFavoritoPorId(Integer id) {
        return favoritoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Favorito com ID " + id + " não encontrado."));
    }

    private Perfil buscarPerfilPorId(Integer perfilId) {
        return perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil com ID " + perfilId + " não encontrado."));
    }

    private Livro buscarLivroPorGoogleId(String googleId) {
        return livroRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new RegraNegocioException("Livro com Google ID " + googleId + " não encontrado."));
    }

    private void validarFavoritoExistente(Integer perfilId, Integer livroId) {
        if (favoritoRepository.existsByPerfilIdAndLivroId(perfilId, livroId)) {
            throw new RegraNegocioException("O perfil já favoritou este livro.");
        }
    }

    private Perfil buscarPerfilPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraNegocioException("Usuário com username " + username + " não encontrado."));

        return perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RegraNegocioException("Perfil para o usuário com username " + username + " não encontrado."));
    }

    private Favoritos criarFavorito(Perfil perfil, Livro livro, String googleId) {
        Favoritos favorito = new Favoritos();
        favorito.setPerfil(perfil);
        favorito.setLivro(livro);
        favorito.setGoogleId(googleId);
        favorito.setDataFavorito(LocalDateTime.now());
        return favorito;
    }

    private FavoritoResponseDTO converterParaDTO(Favoritos favorito) {
        FavoritoResponseDTO dto = new FavoritoResponseDTO();
        dto.setId(favorito.getId());
        dto.setUsername(favorito.getPerfil().getUsuario().getUsername());
        dto.setGoogleId(favorito.getGoogleId());

        Livro livro = favorito.getLivro();
        LivroResumidoDTO livroResumidoDTO = new LivroResumidoDTO(
                livro.getTitulo(),
                livro.getAutores(),
                livro.getDataPublicacao().toString()
        );
        dto.setLivro(livroResumidoDTO);

        dto.setDataFavorito(favorito.getDataFavorito());
        return dto;
    }
}
