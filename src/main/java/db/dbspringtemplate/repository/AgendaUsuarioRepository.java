package db.dbspringtemplate.repository;

import db.dbspringtemplate.model.AgendaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaUsuarioRepository extends JpaRepository<AgendaUsuario, Long> {

    @Query("select a from AgendaUsuario a where a.usuario.id = ?1")
    List<AgendaUsuario> buscarTodaAgendaUsuario(Long id);

    @Query("select a from AgendaUsuario a where a.usuario.id = ?1 and a.id = ?2")
    Optional<AgendaUsuario> buscarHorarioAgenda(Long idUsuario, Long id);

    @Query("select a from AgendaUsuario a where a.usuario.id = ?1 order by a.horarioAgenda")
    List<AgendaUsuario> buscaAgendaOrdenadaPorData(Long id);
}
