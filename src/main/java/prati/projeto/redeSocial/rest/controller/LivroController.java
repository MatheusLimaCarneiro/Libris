package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.LivroService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/livro")
@Tag(
        name = "Livros",
        description = "Endpoints para gestão completa de livros. Permite criar, atualizar, excluir e buscar livros no sistema, além de retornar uma lista paginada com resumos de livros."
)
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(
            summary = "Buscar livro por ID",
            description = "Retorna as informações detalhadas de um livro com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Livro encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Livro> getLivroById(
            @Parameter(description = "ID do livro", example = "1")
            @PathVariable Integer id) {
        Livro livro = livroService.getLivroById(id);
        return new ServiceResponse<>(livro, "Livro encontrado", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Criar novo livro",
            description = "Cria um novo livro com os dados fornecidos.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Livro salvo com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Livro> saveLivro(@RequestBody @Valid Livro livro) {
        Livro savedLivro = livroService.saveLivro(livro);
        return new ServiceResponse<>(savedLivro, "Livro salvo com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Excluir livro",
            description = "Exclui o livro com o ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLivro(
            @Parameter(description = "ID do livro a ser excluído", example = "1")
            @PathVariable Integer id) {
        livroService.deleteLivro(id);
    }

    @Operation(
            summary = "Atualizar livro",
            description = "Atualiza os dados de um livro com base no seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Livro atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> updateLivro(
            @Parameter(description = "ID do livro a ser atualizado", example = "1")
            @PathVariable Integer id,
            @RequestBody @Valid Livro livro) {
        livroService.updateLivro(id, livro);
        return new ServiceResponse<>(null, "Livro atualizado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Buscar livros",
            description = "Busca livros por título ou autor.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Livros encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum livro encontrado")
            }
    )
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<List<Livro>> findLivro(
            @RequestParam(required = false) @Parameter(description = "Título do livro") String titulo,
            @RequestParam(required = false) @Parameter(description = "Autores do livro") String autores) {

        List<Livro> livros = livroService.findLivro(titulo, autores);
        String mensagem = livros.isEmpty() ? "Nenhum livro encontrado" : "Livros encontrados";
        return new ServiceResponse<>(livros.isEmpty() ? null : livros, mensagem, !livros.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar todos os livros",
            description = "Retorna uma lista paginada de livros com resumos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de livros retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "204", description = "Nenhum livro encontrado")
            }
    )
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<LivroResumidoDTO>> getAllLivros(
            @RequestParam(defaultValue = "0") @Parameter(description = "Número da página") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Número de livros por página") int size) {

        Page<LivroResumidoDTO> livros = livroService.getAllLivros(page, size);
        String mensagem = livros.isEmpty() ? "Nenhum livro encontrado" : "Livros encontrados";
        return new ServiceResponse<>(livros, mensagem, !livros.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
