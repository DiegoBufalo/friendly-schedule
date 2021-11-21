package db.dbspringtemplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import db.dbspringtemplate.model.Usuario;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

    private Long id;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "E-mail é obrigatório")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatório")
    private LocalDate dataNascimento;

    @NotNull(message = "cpf é obrigatório")
    private String cpf;

    private boolean ativo;

    public static UsuarioDto fromEntity(Usuario usuario){
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .ativo(usuario.isAtivo())
                .build();
    }

    public static Usuario fromDTO(UsuarioDto usuarioDto){
        return Usuario.builder()
                .id(usuarioDto.getId())
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .dataNascimento(usuarioDto.getDataNascimento())
                .cpf(usuarioDto.getCpf())
                .ativo(usuarioDto.isAtivo())
                .build();
    }

    public static List<UsuarioDto> fromEntity(List<Usuario> usuarios){
        return usuarios.stream().map(UsuarioDto::fromEntity).collect(Collectors.toList());
    }
}
