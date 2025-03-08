package prati.projeto.redeSocial.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.CurtidaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("libris/curtidas")
@Tag(name = "Curtidas", description = "Endpoints que gerenciam as curtidas em comentários e respostas. Permitem que perfis curtam e descurtam esses itens, garantindo que cada perfil possa interagir apenas uma vez por comentário ou resposta. Esses endpoints asseguram o controle e a integridade das interações de curtidas na rede social.")
public class CurtidaController {

    @Autowired
    private CurtidaService curtidaService;

    @Operation(
            summary = "Curtir um comentário",
            description = "Permite que um perfil curta um comentário específico. Se o perfil já tiver curtido o comentário, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário curtido com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou comentário inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou comentário não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil já curtiu este comentário")
            }
    )
    @PostMapping("/comentario/{comentarioId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirComentario(
            @Parameter(description = "ID do perfil que está curtindo o comentário", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do comentário que será curtido", example = "10")
            @PathVariable @Positive(message = "ID do comentário deve ser positivo") Integer comentarioId) {
        curtidaService.curtirComentario(perfilId, comentarioId);
        return new ServiceResponse<>(null, "Comentário curtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Descurtir um comentário",
            description = "Permite que um perfil descurta um comentário específico. Se o perfil não tiver curtido o comentário anteriormente, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário descurtido com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou comentário inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou comentário não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil ainda não curtiu este comentário")
            }
    )
    @DeleteMapping("/comentario/{comentarioId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirComentario(
            @Parameter(description = "ID do perfil que está descurtindo o comentário", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do comentário que será descurtido", example = "10")
            @PathVariable @Positive(message = "ID do comentário deve ser positivo") Integer comentarioId) {
        curtidaService.descurtirComentario(perfilId, comentarioId);
        return new ServiceResponse<>(null, "Comentário descurtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Curtir uma resposta",
            description = "Permite que um perfil curta uma resposta específica. Se o perfil já tiver curtido a resposta, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resposta curtida com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou resposta inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou resposta não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil já curtiu esta resposta")
            }
    )
    @PostMapping("/resposta/{respostaId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirResposta(
            @Parameter(description = "ID do perfil que está curtindo a resposta", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID da resposta que será curtida", example = "5")
            @PathVariable @Positive(message = "ID da resposta deve ser positivo") Integer respostaId) {
        curtidaService.curtirResposta(perfilId, respostaId);
        return new ServiceResponse<>(null, "Resposta curtida com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Descurtir uma resposta",
            description = "Permite que um perfil descurta uma resposta específica. Se o perfil não tiver curtido a resposta anteriormente, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Resposta descurtida com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou resposta inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou resposta não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil ainda não curtiu esta resposta")
            }
    )
    @DeleteMapping("/resposta/{respostaId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirResposta(
            @Parameter(description = "ID do perfil que está descurtindo a resposta", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID da resposta que será descurtida", example = "5")
            @PathVariable @Positive(message = "ID da resposta deve ser positivo") Integer respostaId) {
        curtidaService.descurtirResposta(perfilId, respostaId);
        return new ServiceResponse<>(null, "Resposta descurtida com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Curtir um post",
            description = "Permite que um perfil curta um post específico. Se o perfil já tiver curtido o post, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post curtido com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou post inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou post não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil já curtiu este post")
            }
    )
    @PostMapping("/post/{postId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirPost(
            @Parameter(description = "ID do perfil que está curtindo o post", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do post que será curtido", example = "10")
            @PathVariable @Positive(message = "ID do post deve ser positivo") Integer postId) {
        curtidaService.curtirPost(perfilId, postId);
        return new ServiceResponse<>(null, "Post curtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Descurtir um post",
            description = "Permite que um perfil descurta um post específico. Se o perfil não tiver curtido o post anteriormente, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Post descurtido com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou post inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou post não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil ainda não curtiu este post")
            }
    )
    @DeleteMapping("/post/{postId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirPost(
            @Parameter(description = "ID do perfil que está descurtindo o post", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do post que será descurtido", example = "10")
            @PathVariable @Positive(message = "ID do post deve ser positivo") Integer postId) {
        curtidaService.descurtirPost(perfilId, postId);
        return new ServiceResponse<>(null, "Post descurtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Curtir um comentário do fórum",
            description = "Permite que um perfil curta um comentário específico do fórum. Se o perfil já tiver curtido o comentário, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comentário do fórum curtido com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class))
                    ),
                            @ApiResponse(responseCode = "400", description = "ID do perfil ou comentário inválido"),
                            @ApiResponse(responseCode = "404", description = "Perfil ou comentário não encontrado"),
                            @ApiResponse(responseCode = "409", description = "Perfil já curtiu este comentário")
                            }
                    )

                    @PostMapping("/comentario-forum/{comentarioForumId}/perfil/{perfilId}")
                    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirComentarioForum(
            @Parameter(description = "ID do perfil que está curtindo o comentário do fórum", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do comentário do fórum que será curtido", example = "10")
            @PathVariable @Positive(message = "ID do comentário do fórum deve ser positivo") Integer comentarioForumId) {
        curtidaService.curtirComentarioForum(perfilId, comentarioForumId);
        return new ServiceResponse<>(null, "Comentário do fórum curtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Descurtir um comentário do fórum",
            description = "Permite que um perfil descurta um comentário específico do fórum. Se o perfil não tiver curtido o comentário anteriormente, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Comentário do fórum descurtido com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou comentário inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou comentário não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil ainda não curtiu este comentário")
            }
    )
    @DeleteMapping("/comentario-forum/{comentarioForumId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirComentarioForum(
            @Parameter(description = "ID do perfil que está descurtindo o comentário do fórum", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID do comentário do fórum que será descurtido", example = "10")
            @PathVariable @Positive(message = "ID do comentário do fórum deve ser positivo") Integer comentarioForumId) {
        curtidaService.descurtirComentarioForum(perfilId, comentarioForumId);
        return new ServiceResponse<>(null, "Comentário do fórum descurtido com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Curtir uma resposta do fórum",
            description = "Permite que um perfil curta uma resposta específica do fórum. Se o perfil já tiver curtido a resposta, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resposta do fórum curtida com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou resposta inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou resposta não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil já curtiu esta resposta")
            }
    )
    @PostMapping("/resposta-forum/{respostaForumId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirRespostaForum(
            @Parameter(description = "ID do perfil que está curtindo a resposta do fórum", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID da resposta do fórum que será curtida", example = "5")
            @PathVariable @Positive(message = "ID da resposta do fórum deve ser positivo") Integer respostaForumId) {
        curtidaService.curtirRespostaForum(perfilId, respostaForumId);
        return new ServiceResponse<>(null, "Resposta do fórum curtida com sucesso", true, getFormattedTimestamp());
    }

    @Operation(
            summary = "Descurtir uma resposta do fórum",
            description = "Permite que um perfil descurta uma resposta específica do fórum. Se o perfil não tiver curtido a resposta anteriormente, uma exceção será lançada.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Resposta do fórum descurtida com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "ID do perfil ou resposta inválido"),
                    @ApiResponse(responseCode = "404", description = "Perfil ou resposta não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Perfil ainda não curtiu esta resposta")
            }
    )
    @DeleteMapping("/resposta-forum/{respostaForumId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirRespostaForum(
            @Parameter(description = "ID do perfil que está descurtindo a resposta do fórum", example = "1")
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @Parameter(description = "ID da resposta do fórum que será descurtida", example = "5")
            @PathVariable @Positive(message = "ID da resposta do fórum deve ser positivo") Integer respostaForumId) {
        curtidaService.descurtirRespostaForum(perfilId, respostaForumId);
        return new ServiceResponse<>(null, "Resposta do fórum descurtida com sucesso", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}