package db.dbspringtemplate.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agenda_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AgendaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_usario_id_gen")
    @SequenceGenerator(name = "agenda_usario_id_gen", sequenceName = "agenda_usuario_id_seq")
    private Long id;

    @ManyToOne(targetEntity = Usuario.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "horarios_agenda")
    private LocalDateTime horarioAgenda;

    @Column(name = "ocupado")
    private boolean ocupado;

    @Column(name = "mensagem")
    private String mensagem;
}