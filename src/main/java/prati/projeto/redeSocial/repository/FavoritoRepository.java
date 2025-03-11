package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Favoritos;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;

public interface FavoritoRepository extends JpaRepository<Favoritos, Integer> {

    Page<Favoritos> findByPerfilAndLivro(Perfil perfil, Livro livro, Pageable pageable);

    Page<Favoritos> findByLivro(Livro livro, Pageable pageable);

    Page<Favoritos> findByPerfil(Perfil perfil, Pageable pageable);

    boolean existsByPerfilIdAndLivroId(Integer perfilId, Integer livroId);
}
