package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "AtividadePerfilDTO",
        description = "DTO que representa a atividade de um perfil no sistema. " +
                "Contém informações como a data da atividade, a quantidade de atividades realizadas naquela data, " +
                "e o username do usuário associado à atividade."
)
public class AtividadePerfilDTO {

    @Schema(
            description = "Data da atividade no formato 'yyyy-MM-dd'.",
            example = "2023-10-15"
    )
    private LocalDate data;

    @Schema(
            description = "Quantidade de atividades realizadas na data especificada.",
            example = "5"
    )
    private Integer quantidade;

    @Schema(
            description = "Username do usuário associado à atividade.",
            example = "joao_silva"
    )
    private String username;
}