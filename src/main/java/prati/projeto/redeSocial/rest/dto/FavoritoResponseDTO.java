package prati.projeto.redeSocial.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoritoResponseDTO {

    private Integer id;
    private String username;
    private String googleId;
    private LivroResumidoDTO livro;
    private LocalDateTime dataFavorito;
}
