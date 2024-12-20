package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 100)
    private String titulo;

    @Column(length = 100)
    private String autor;

    @Column(length = 13, unique = true)
    private String isbn;

    @Column(length = 2000)
    private String sinopse;

    private LocalDate dataPublicacao;
}
