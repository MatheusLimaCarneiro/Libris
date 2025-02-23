package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para criação ou atualização de uma resenha.
 * <p>
 * Este objeto encapsula as informações necessárias para a criação ou atualização de uma resenha,
 * incluindo o perfil que a criou, o livro associado, título, autor, texto e nota atribuída.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para criação ou atualização de uma resenha")
public class ResenhaDTO {


    @NotNull(message = " O ID do perfil é obrigatório")
    @Schema(
            description = "Identificador do perfil",
            example = "1"
    )
    private Integer perfilId;


    @NotNull(message = "O ID do livro é obrigatório")
    @Schema(
            description = "Identificador do livro",
            example = "101"
    )
    private Integer livroId;


    @NotEmpty(message = "O título é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    @Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
    @Schema(
            description = "Título da resenha",
            example = "Análise do Livro"
    )
    private String titulo;


    @NotEmpty(message = "O nome do autor é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O autor não pode conter apenas espaços.")
    @Size(max = 100, message = "O nome do autor pode ter no máximo 100 caracteres")
    @Schema(
            description = "Nome do autor da resenha",
            example = "João da Silva"
    )
    private String autor;


    @NotEmpty(message = "O texto da resenha é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O Texto não pode conter apenas espaços.")
    @Schema(
            description = "Conteúdo da resenha",
            example = "Este livro é muito interessante por...")
    private String texto;


    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    @Schema(
            description = "Nota da resenha (entre 1 e 5)",
            example = "4.5"
    )
    private Double nota;
}
