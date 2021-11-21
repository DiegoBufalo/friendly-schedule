package db.dbspringtemplate.service.agendausuario;

import db.dbspringtemplate.dto.AgendaUsuarioDto;
import db.dbspringtemplate.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public interface AgendaUsuarioService {

    List<AgendaUsuarioDto> buscarAgendaUsuario(Long usuarioId);
    AgendaUsuarioDto inserirItemNaAgenda(Long usuarioId, AgendaUsuarioDto agendaUsuarioDto);
    void rematerializarAgendaUsuario(Long usuarioId, Integer diasARematerializar);
    void limparAgenda(Long usuarioId);
    void materializarAgendaUsuario(Usuario usuario, LocalDate dataInicial, LocalDate dataFinal);
}
