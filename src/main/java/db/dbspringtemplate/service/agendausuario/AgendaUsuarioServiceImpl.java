package db.dbspringtemplate.service.agendausuario;

import db.dbspringtemplate.dto.AgendaUsuarioDto;
import db.dbspringtemplate.error.RestException;
import db.dbspringtemplate.model.AgendaUsuario;
import db.dbspringtemplate.model.Usuario;
import db.dbspringtemplate.repository.AgendaUsuarioRepository;
import db.dbspringtemplate.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class AgendaUsuarioServiceImpl implements AgendaUsuarioService {

    private final AgendaUsuarioRepository agendaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AgendaUsuarioServiceImpl(AgendaUsuarioRepository agendaUsuarioRepository,
                                    UsuarioRepository usuarioRepository) {
        this.agendaUsuarioRepository = agendaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendaUsuarioDto> buscarAgendaUsuario(Long usuarioId) {
        List<AgendaUsuario> agendaUsuarios =
                this.agendaUsuarioRepository.buscarTodaAgendaUsuario(usuarioId);

        return AgendaUsuarioDto.fromEntity(agendaUsuarios);
    }

    @Override
    public void inserirItemNaAgenda(Long usuarioId, AgendaUsuarioDto agendaUsuarioDto) {

        AgendaUsuario agendaUsuario = agendaUsuarioRepository
                .buscarHorarioAgenda(usuarioId, agendaUsuarioDto.getId()).orElseThrow(() -> {
            throw new RestException(HttpStatus.NOT_FOUND, "Dia não materializado na agenda");
        });

        agendaUsuario.setMensagem(agendaUsuarioDto.getMensagem());
        agendaUsuario.setOcupado(true);
    }

    @Override
    public void rematerializarAgendaUsuario(Long usuarioId, Integer diasARematerializar) {

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> {
            throw new RestException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        });

        List<AgendaUsuario> agendaUsuarios = agendaUsuarioRepository.buscaAgendaOrdenadaPorData(usuarioId);

        AgendaUsuario ultimoDiaMaterializado = agendaUsuarios.get(agendaUsuarios.size() -1);

        this.materializarAgendaUsuario(usuario,
                LocalDate.from(ultimoDiaMaterializado.getHorarioAgenda().plusDays(1)),
                LocalDate.from(ultimoDiaMaterializado.getHorarioAgenda().plusDays(diasARematerializar).plusDays(1)));
    }

    @Override
    public void limparAgenda(Long usuarioId) {
        List<AgendaUsuario> agendaUsuario = agendaUsuarioRepository.buscarTodaAgendaUsuario(usuarioId);
        if (agendaUsuario.isEmpty())
            throw new RestException(HttpStatus.NOT_FOUND, "Usuário não possui agenda");

        List<AgendaUsuario> agendaADeletar = new ArrayList<>();

        agendaUsuario.forEach(x -> {
            if (x.getHorarioAgenda().isBefore(LocalDateTime.now())) {
                agendaADeletar.add(x);
            } else {
                x.setOcupado(false);
                x.setMensagem(null);
            }
        });

        agendaUsuarioRepository.deleteAll(agendaADeletar);
    }

    @Override
    public void materializarAgendaUsuario(Usuario usuario, LocalDate dataInicial, LocalDate dataFinal) {
        try {
            LocalDateTime horaInicial = LocalDateTime.parse(dataInicial + "T00:00:00");
            LocalDateTime horaFinal = LocalDateTime.parse(dataFinal + "T00:00:00");

            List<AgendaUsuario> agendasUsuario = new ArrayList<>();

            while (horaInicial.isBefore(horaFinal)) {
                AgendaUsuario agenda = new AgendaUsuario();
                agenda.setUsuario(usuario);
                agenda.setMensagem(null);
                agenda.setOcupado(false);
                agenda.setHorarioAgenda(horaInicial);
                agendasUsuario.add(agenda);

                horaInicial = horaInicial.plusHours(1);
            }

            agendaUsuarioRepository.saveAll(agendasUsuario);
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Erro ao materializar a agenda");
        }
    }
}
