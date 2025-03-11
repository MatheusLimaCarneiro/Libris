package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.AtividadePerfilService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/atividade")
@Tag(
        name = "Atividades do Perfil",
        description = "Endpoints para gerenciar as atividades de um perfil. " +
                "Permite listar as atividades recentes de um perfil, com opção de paginação."
)
public class AtividadePerfilController {

    @Autowired
    private AtividadePerfilService atividadePerfilService;

    @Operation(
            summary = "Listar Atividades Recentes",
            description = "Retorna uma lista das atividades recentes de um perfil, filtradas pelos últimos N dias.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Atividades encontradas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhuma atividade encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<List<AtividadePerfilDTO>> listarAtividades(
            @Parameter(description = "ID do perfil", example = "1", required = true)
            @PathVariable Integer perfilId,

            @Parameter(description = "Número de dias para filtrar as atividades", example = "60")
            @RequestParam(defaultValue = "60") int dias
    ) {
        List<AtividadePerfilDTO> atividades = atividadePerfilService.consultarUltimasAtividades(perfilId, dias);
        String mensagem = atividades.isEmpty() ? "Nenhuma atividade encontrada" : "Atividades encontradas";
        return new ServiceResponse<>(atividades, mensagem, !atividades.isEmpty(), getFormattedTimestamp());
    }

    @Operation(
            summary = "Listar Atividades Recentes Paginadas",
            description = "Retorna uma lista paginada das atividades recentes de um perfil, filtradas pelos últimos N dias.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Atividades encontradas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhuma atividade encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/paginado/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AtividadePerfilDTO>> listarAtividadesPaginadas(
            @Parameter(description = "ID do perfil", example = "1", required = true)
            @PathVariable Integer perfilId,

            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Tamanho da página", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Número de dias para filtrar as atividades", example = "60")
            @RequestParam(defaultValue = "60") int dias
    ) {
        Page<AtividadePerfilDTO> atividadesPage = atividadePerfilService.listarAtividadesPaginadas(perfilId, page, size, dias);
        String mensagem = atividadesPage.isEmpty() ? "Nenhuma atividade encontrada nos últimos " + dias + " dias" : "Atividades encontradas";
        return new ServiceResponse<>(atividadesPage, mensagem, !atividadesPage.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}