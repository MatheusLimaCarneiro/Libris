package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Favoritos",
        description = "Endpoints que gerenciam os favoritos dos usuários. " +
                "Permite favoritar, desfavoritar e listar livros favoritos de um perfil. " +
                "Esses endpoints fornecem funcionalidades para que os usuários possam gerenciar seus livros favoritos."
)
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Operation(
            summary = "Favoritar Livro",
            description = "Adiciona um livro à lista de favoritos de um perfil.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Livro favoritado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Erro de validação dos dados fornecidos")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<FavoritoResponseDTO> favoritarLivro(@RequestBody @Valid FavoritoRequestDTO dto) {
        FavoritoResponseDTO favoritoDTO = favoritoService.favoritarLivro(dto);
        return new ServiceResponse<>(favoritoDTO, "Livro favoritado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Desfavoritar Livro",
            description = "Remove um livro da lista de favoritos de um perfil.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Livro desfavoritado com sucesso"
                    ),
                    @ApiResponse(responseCode = "404", description = "Favorito não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desfavoritarLivro(@Parameter(description = "ID do favorito", example = "1") @PathVariable Integer id) {
        favoritoService.desfavoritarLivro(id);
    }

    @Operation(
            summary = "Listar Favoritos por Perfil",
            description = "Retorna uma lista paginada de livros favoritos de um perfil específico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Favoritos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum favorito encontrado")
            }
    )
                    @GetMapping("/perfil/{perfilId}")
                    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> listarFavoritosPorPerfil(
            @Parameter(description = "ID do perfil", example = "1") @PathVariable Integer perfilId,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Número de itens por página", example = "10") @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.listarFavoritosPorPerfil(perfilId, page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Filtrar Favoritos",
            description = "Retorna uma lista paginada de favoritos filtrados por username e/ou Google ID do livro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Favoritos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum favorito encontrado")
            }
    )
    @GetMapping("/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> filtrarFavoritos(
            @Parameter(description = "Username do perfil", example = "usuario123") @RequestParam(required = false) String username,
            @Parameter(description = "Google ID do livro", example = "XYZ123") @RequestParam(required = false) String googleId,
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Número de itens por página", example = "10") @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.filtrarFavoritos(username, googleId, page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Todos os Favoritos",
            description = "Retorna uma lista paginada de todos os favoritos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Favoritos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum favorito encontrado")
            }
    )
                    @GetMapping("/listar")
                    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<FavoritoResponseDTO>> listarTodosFavoritos(
            @Parameter(description = "Número da página", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Número de itens por página", example = "10") @RequestParam(defaultValue = "10") int size) {

        Page<FavoritoResponseDTO> favoritos = favoritoService.listarTodosFavoritos(page, size);
        String mensagem = favoritos.isEmpty() ? "Nenhum favorito encontrado" : "Favoritos encontrados";
        return new ServiceResponse<>(favoritos, mensagem, !favoritos.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}