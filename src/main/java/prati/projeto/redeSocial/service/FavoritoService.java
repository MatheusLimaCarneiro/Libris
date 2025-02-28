package prati.projeto.redeSocial.service;

import org.springframework.data.domain.Page;
import prati.projeto.redeSocial.rest.dto.FavoritoRequestDTO;
import prati.projeto.redeSocial.rest.dto.FavoritoResponseDTO;

public interface FavoritoService{

    FavoritoResponseDTO getFavoritoById(Integer id);

    FavoritoResponseDTO favoritarLivro(FavoritoRequestDTO dto);

    void desfavoritarLivro(Integer id);

    Page<FavoritoResponseDTO> listarFavoritosPorPerfil(Integer perfilId, int page, int size);

    Page<FavoritoResponseDTO> listarTodosFavoritos(int page, int size);

    Page<FavoritoResponseDTO> filtrarFavoritos(String username, String googleId, int page, int size);
}
