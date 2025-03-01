package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.FavoritoRequestDTO;
import prati.projeto.redeSocial.rest.dto.FavoritoResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.FavoritoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<FavoritoResponseDTO> favoritarLivro(@RequestBody @Valid FavoritoRequestDTO dto) {
        FavoritoResponseDTO favoritoDTO = favoritoService.favoritarLivro(dto);
        return new ServiceResponse<>(favoritoDTO, "Livro favoritado com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desfavoritarLivro(@PathVariable Integer id) {
        favoritoService.desfavoritarLivro(id);
    }

    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> listarFavoritosPorPerfil(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.listarFavoritosPorPerfil(perfilId, page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> filtrarFavoritos(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String googleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.filtrarFavoritos(username, googleId, page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> listarTodosFavoritos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.listarTodosFavoritos(page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}