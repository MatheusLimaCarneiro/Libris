package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa o status de leitura de um livro por um usuário.")
public class StatusLeituraDTO {

    @Schema(description = "ID do status de leitura", example = "1")
    private Integer id;

    @NotNull(message = "O ID do perfil é obrigatório")
    @Schema(description = "ID do perfil do usuário", example = "123")
    private Integer perfilId;

    @NotNull(message = "O ID do livro é obrigatório")
    @Schema(description = "ID do livro associado", example = "456")
    private Integer livroId;

    @NotNull(message = "A página do livro é obrigatória")
    @Min(value = 1, message = "A página deve ser maior ou igual a 1")
    @Schema(description = "Página atual do livro", example = "120")
    private Integer pagina;

    @NotNull(message = "O status da leitura é obrigatório")
    @Schema(description = "Status da leitura do livro", example = "LENDO")
    private StatusLeituraEnum status;
}
