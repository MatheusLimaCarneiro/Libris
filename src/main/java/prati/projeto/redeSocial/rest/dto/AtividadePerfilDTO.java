package prati.projeto.redeSocial.rest.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtividadePerfilDTO {
    private LocalDate data;
    private Integer quantidade;
    private String username;
}
