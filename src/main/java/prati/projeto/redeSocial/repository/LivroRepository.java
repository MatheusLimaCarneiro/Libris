package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
