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
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.PostForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/posts")
@Tag(
        name = "Posts do Fórum",
        description = "Endpoints para gerenciar posts no fórum. " +
                "Permite criar, buscar, listar, filtrar e deletar posts. " +
                "Os posts podem ser associados a tags, usuários e livros, facilitando a organização e busca."
)
public class PostForumController {

    @Autowired
    private PostForumService postForumService;

    @Operation(
            summary = "Criar Post",
            description = "Cria um novo post no fórum. O post deve conter informações como o ID do perfil, o texto e outras informações relevantes.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post criado com sucesso",
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
    public ServiceResponse<PostForumResponseDTO> criarPost(
            @Parameter(description = "DTO contendo os dados do post", required = true)
            @RequestBody @Valid PostForumRequestDTO dto) {
        PostForumResponseDTO postDTO = postForumService.criarPost(dto);
        return new ServiceResponse<>(postDTO, "Post criado com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Buscar Post por ID",
            description = "Busca um post específico pelo seu ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post encontrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Post não encontrado")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<PostForumResponseDTO> buscarPorId(
            @Parameter(description = "ID do post a ser buscado", example = "1", required = true)
            @PathVariable Integer id) {
        PostForumResponseDTO postDTO = postForumService.buscarPorId(id);
        return new ServiceResponse<>(postDTO, "Post encontrado", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Todos os Posts",
            description = "Retorna uma lista paginada de todos os posts no fórum.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Posts encontrados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum post encontrado")
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PostForumResponseDTO>> listarTodos(
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Page<PostForumResponseDTO> posts = postForumService.listarTodos(page, size);
        String mensagem = posts.isEmpty() ? "Nenhum post encontrado" : "Posts encontrados";
        return new ServiceResponse<>(posts, mensagem, !posts.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar Post",
            description = "Deleta um post específico pelo seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Post deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Post não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPost(
            @Parameter(description = "ID do post a ser deletado", example = "1", required = true)
            @PathVariable Integer id) {
        postForumService.deletarPost(id);
    }

    @Operation(
            summary = "Filtrar Posts",
            description = "Retorna uma lista paginada de posts filtrados por tags, username ou nome do livro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Posts encontrados com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum post encontrado")
            }
    )
    @GetMapping("/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PostForumResponseDTO>> filtrarPosts(
            @Parameter(description = "Tags para filtrar os posts", example = "java,spring")
            @RequestParam(required = false) String tags,
            @Parameter(description = "Username do autor do post", example = "usuario123")
            @RequestParam(required = false) String username,
            @Parameter(description = "Nome do livro associado ao post", example = "Clean Code")
            @RequestParam(required = false) String livroNome,
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Page<PostForumResponseDTO> posts = postForumService.filtrarPosts(tags, username, livroNome, page, size);
        String mensagem = posts.isEmpty() ? "Nenhum post encontrado" : "Posts encontrados";
        return new ServiceResponse<>(posts, mensagem, !posts.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}