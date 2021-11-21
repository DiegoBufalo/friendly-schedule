package db.dbspringtemplate.repository;

import db.dbspringtemplate.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.cpf = ?1")
    Optional<Usuario> buscarPorCpf(String cpf);

    @Query("select u from Usuario u where u.email = ?1")
    Optional<Usuario> buscarPorEmail(String email);
}
