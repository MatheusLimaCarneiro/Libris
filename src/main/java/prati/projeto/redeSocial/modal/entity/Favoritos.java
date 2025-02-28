package prati.projeto.redeSocial.modal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro_favorito")
public class Favoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do favorito", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    @Schema(description = "Perfil do usuário que favoritou o livro")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    @Schema(description = "Livro favoritado")
    private Livro livro;

    @Column(unique = true)
    @Schema(description = "Google ID do livro", example = "abc123")
    private String googleId;

    @Column(name = "data_favorito")
    @Schema(description = "Data em que o livro foi favoritado", example = "2023-10-01T12:00:00")
    private LocalDateTime dataFavorito;

}
