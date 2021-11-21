package db.dbspringtemplate.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usario_id_gen")
    @SequenceGenerator(name = "usario_id_gen", sequenceName = "usuario_id_seq")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "cpf", unique = true, length = 11)
    private String cpf;

    @Column(name = "ativo")
    private boolean ativo;

    @OneToMany(mappedBy = "usuario", targetEntity = AgendaUsuario.class)
    private List<AgendaUsuario> agendaUsuario;
}
