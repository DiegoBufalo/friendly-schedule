package db.dbspringtemplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import db.dbspringtemplate.model.AgendaUsuario;
import db.dbspringtemplate.model.Usuario;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaUsuarioDto {

    private Long id;

    private Usuario usuario;

    private LocalDateTime horarioAgenda;

    private boolean ocupado;

    private String mensagem;

    public static AgendaUsuarioDto fromEntity(AgendaUsuario agendaUsuario){
        return AgendaUsuarioDto.builder()
                .id(agendaUsuario.getId())
                .horarioAgenda(agendaUsuario.getHorarioAgenda())
                .ocupado(agendaUsuario.isOcupado())
                .mensagem(agendaUsuario.getMensagem())
                .build();
    }

    public static List<AgendaUsuarioDto> fromEntity(List<AgendaUsuario> agendasUsuario){
        return agendasUsuario.stream().map(AgendaUsuarioDto::fromEntity).toList();
    }
}
