package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.RelacionamentoSeguidoresService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/relacionamentos")
@Tag(
        name = "Relacionamento Seguidores",
        description = "Endpoints responsáveis pelas operações de seguimento entre perfis."
)
public class RelacionamentoSeguidoresController {

    @Autowired
    private RelacionamentoSeguidoresService relacionamentoService;


    @Operation(
            summary = "Seguir perfil",
            description = "Permite que um perfil (seguidor) siga outro perfil (seguido).",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Perfil seguido com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "IDs inválidos ou dados de entrada incorretos.")
            }
    )
    @PostMapping("/seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> seguirPerfil(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.seguirPerfil(seguidorId, seguidoId);
        return new ServiceResponse<>(null, "Perfil seguido com sucesso", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Deixar de seguir perfil",
            description = "Permite que um perfil deixe de seguir outro perfil.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso; nenhum conteúdo retornado."),
                    @ApiResponse(responseCode = "400", description = "IDs inválidos ou dados de entrada incorretos.")
            }
    )
    @DeleteMapping("/deixar-de-seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deixarDeSeguir(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.deixarDeSeguir(seguidorId, seguidoId);
    }



    @Operation(
            summary = "Verificar seguimento",
            description = "Verifica se um perfil está seguindo outro.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verificação realizada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/esta-seguindo/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Boolean> estaSeguindo(
            @PathVariable Integer seguidorId,
            @PathVariable Integer seguidoId) {
        boolean resultado = relacionamentoService.estaSeguindo(seguidorId, seguidoId);
        return new ServiceResponse<>(resultado, "Verificação de seguimento", true, getFormattedTimestamp());
    }



    @Operation(
            summary = "Buscar seguidores",
            description = "Lista os seguidores de um perfil com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seguidores encontrados.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado.")
            }
    )
    @GetMapping("/seguidores/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PerfilResumidoDTO>> buscarSeguidores(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PerfilResumidoDTO> seguidores = relacionamentoService.buscarSeguidores(perfilId, page, size);
        String mensagem = seguidores.isEmpty() ? "Nenhum seguidor encontrado" : "Seguidores encontrados";
        return new ServiceResponse<>(seguidores, mensagem, !seguidores.isEmpty(), getFormattedTimestamp());
    }



    @Operation(
            summary = "Buscar perfis seguidos",
            description = "Lista os perfis que um determinado perfil está seguindo, com suporte a paginação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Seguindo encontrados.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Perfil não encontrado.")
            }
    )
    @GetMapping("/seguindo/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PerfilResumidoDTO>> buscarSeguindo(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PerfilResumidoDTO> seguindo = relacionamentoService.buscarSeguindo(perfilId, page, size);
        String mensagem = seguindo.isEmpty() ? "Não está seguindo ninguém" : "Seguindo encontrados";
        return new ServiceResponse<>(seguindo, mensagem, !seguindo.isEmpty(), getFormattedTimestamp());
    }



    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}