package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/resenhas")
@Tag(
        name = "Resenha",
        description = "Endpoints responsáveis pela resenha dos livros."
)
public class ResenhaController {

    @Autowired
    private ResenhaService resenhaService;

    @Operation(
            summary = "Registra uma nova resenha",
            description = "Registra uma nova resenha e retorna o identificador da resenha cadastrada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resenha salva com sucesso!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro da resenha.")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ResenhaViewDTO> save(@RequestBody @Valid ResenhaDTO dto) {
        ResenhaViewDTO resenhaId = resenhaService.saveResenha(dto);
        return new ServiceResponse<>(resenhaId, "Resenha salva com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Obter resenha por ID",
            description = "Retorna os detalhes da resenha com base no identificador fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenha encontrada!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada.")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ResenhaViewDTO> getById(@PathVariable Integer id) {
        ResenhaViewDTO resenha = resenhaService.getResenhaById(id);
        return new ServiceResponse<>(resenha, "Resenha encontrada!", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Deletar resenha",
            description = "Remove a resenha com base no identificador fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resenha deletada com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada.")
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        resenhaService.deleteResenha(id);
    }

    @Operation(
            summary = "Atualizar resenha",
            description = "Atualiza os dados da resenha existente e retorna mensagem de sucesso.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenha atualizada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização."),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada.")
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> update(@PathVariable Integer id, @Valid @RequestBody ResenhaDTO dto) {
        resenhaService.updateResenha(id, dto);
        return new ServiceResponse<>(null, "Resenha atualizada com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar resenhas por livro",
            description = "Lista todas as resenhas de um livro específico, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenhas do livro encontradas.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
            }
    )
    @GetMapping("/livro/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ResenhaViewDTO>> findByLivro(
            @PathVariable String livroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ResenhaViewDTO> resenhasPage = resenhaService.findByGoogleId(livroId, page, size);
        return new ServiceResponse<>(resenhasPage, "Resenhas do livro encontradas", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar todas as resenhas",
            description = "Lista todas as resenhas existentes, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenhas encontradas.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ResenhaViewDTO>> getAllResenha(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ResenhaViewDTO> resenhas = resenhaService.findAllResenhas(page, size);
        String mensagem = resenhas.isEmpty() ? "Nenhuma resenha encontrada" : "Resenhas encontradas";
        return new ServiceResponse<>(resenhas, mensagem, !resenhas.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}