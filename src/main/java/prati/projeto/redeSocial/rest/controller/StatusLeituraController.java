package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.StatusLeituraService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/status")
@Tag(
        name = "Status de Leitura",
        description = "Endpoints que gerenciam o status de leitura de livros dos usuários. " +
                "Permite criar, atualizar e listar os status de leitura de livros, incluindo o acompanhamento da página em que o usuário parou." +
                " O status de leitura ajuda a monitorar o progresso de leitura e o estado do livro em relação ao usuário.")
public class StatusLeituraController {

    @Autowired
    private StatusLeituraService statusLeituraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar Status de Leitura", description = "Cria um novo status de leitura para um livro.")
    @ApiResponse(responseCode = "201", description = "Status de leitura criado com sucesso.")
    public ServiceResponse<StatusLeituraDTO> criarStatus(@RequestBody @Valid StatusLeituraDTO dto) {
        StatusLeituraDTO statusCriado = statusLeituraService.salvarStatus(dto.getPerfilId(), dto.getGoogleId(), dto.getStatus(), dto.getPagina());
        return new ServiceResponse<>(statusCriado, "Status de leitura criado com sucesso", true, getFormattedTimestamp());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar Status de Leitura", description = "Atualiza o status de leitura e a página de um livro.")
    @ApiResponse(responseCode = "200", description = "Status de leitura atualizado com sucesso.")
    public ServiceResponse<StatusLeituraDTO> mudarStatus(@PathVariable Integer id, @RequestBody @Valid StatusLeituraDTO dto) {
        StatusLeituraDTO statusAtualizado = statusLeituraService.mudarStatus(id, dto.getPagina(), dto.getStatus());
        return new ServiceResponse<>(statusAtualizado, "Status de leitura atualizado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listar Status de Leitura", description = "Retorna uma lista paginada de status de leitura.")
    @ApiResponse(responseCode = "200", description = "Lista de status de leitura retornada com sucesso.")
    public ServiceResponse<Page<StatusLeituraDTO>> getAllStatus(
            @RequestParam(defaultValue = "0") @Parameter(description = "Número da página") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Número de itens por página") int size) {

        Page<StatusLeituraDTO> status = statusLeituraService.listarStatus(page, size);
        String mensagem = status.isEmpty() ? "Nenhum status de leitura encontrado" : "Status de leitura encontrados";

        return new ServiceResponse<>(status, mensagem, !status.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/perfil/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Listar Status de Leitura por Perfil",
            description = "Retorna uma lista paginada de status de leitura associados ao perfil de um usuário, com base no username."
    )
    @ApiResponse(responseCode = "200", description = "Lista de status de leitura retornada com sucesso.")
    @ApiResponse(responseCode = "404", description = "Usuário ou perfil não encontrado.")
    public ServiceResponse<Page<StatusLeituraDTO>> listarStatusPorPerfil(
            @PathVariable @Parameter(description = "Username do usuário associado ao perfil", example = "usuario123") String username,
            @RequestParam(defaultValue = "0") @Parameter(description = "Número da página", example = "0") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Número de itens por página", example = "10") int size) {

        Page<StatusLeituraDTO> status = statusLeituraService.listarStatusPorPerfil(username, page, size);
        String mensagem = status.isEmpty() ? "Nenhum status de leitura encontrado para o perfil" : "Status de leitura encontrados para o perfil";

        return new ServiceResponse<>(status, mensagem, !status.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
