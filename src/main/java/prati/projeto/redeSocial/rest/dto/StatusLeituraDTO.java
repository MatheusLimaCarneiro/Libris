package prati.projeto.redeSocial.rest.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusLeituraDTO {

    private Integer id;

    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @NotNull(message = "O ID do livro é obrigatório")
    private Integer livroId;

    @NotNull(message = "A pagina do livro é obrigatório")
    @Min(value = 1, message = "A página deve ser maior ou igual a 1")
    private Integer pagina;

    @NotNull(message = "O status da leitura é obrigatório")
    private StatusLeituraEnum status;
}
