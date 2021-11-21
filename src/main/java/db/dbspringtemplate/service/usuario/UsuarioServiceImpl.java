package db.dbspringtemplate.service.usuario;

import db.dbspringtemplate.dto.UsuarioDto;
import db.dbspringtemplate.error.RestException;
import db.dbspringtemplate.model.Usuario;
import db.dbspringtemplate.repository.UsuarioRepository;
import db.dbspringtemplate.service.agendausuario.AgendaUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static db.dbspringtemplate.dto.UsuarioDto.fromDTO;
import static db.dbspringtemplate.dto.UsuarioDto.fromEntity;

@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

    private final UsuarioRepository usuarioRepository;
    private final AgendaUsuarioService agendaUsuarioService;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              AgendaUsuarioService agendaUsuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.agendaUsuarioService = agendaUsuarioService;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto buscarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            throw new RestException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO);
        });
        return UsuarioDto.fromEntity(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> buscarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDto::fromEntity).toList();
    }

    @Override
    public UsuarioDto criarUsuario(UsuarioDto usuarioDto) {

        usuarioRepository.buscarPorCpf(usuarioDto.getCpf()).ifPresent(x -> {
            throw new RestException(HttpStatus.CONFLICT, "CPF já cadastrado");
        });
        usuarioRepository.buscarPorEmail(usuarioDto.getEmail()).ifPresent(x -> {
            throw new RestException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        });

        Usuario usuario = fromDTO(usuarioDto);
        usuario.setAtivo(true);
        Usuario save = usuarioRepository.save(usuario);

        try {
            this.agendaUsuarioService.materializarAgendaUsuario(
                    save,
                    LocalDate.now(),
                    LocalDate.now().plusDays(31));
        } catch (IllegalArgumentException e){
            throw new RestException(HttpStatus.CONFLICT, "Impossível materializar uma nova agenda, por favor tente mais tarde");
        }

        return fromEntity(save);
    }

    @Override
    public UsuarioDto atualizarUsuario(Long id, UsuarioDto usuarioDto) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            throw new RestException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO);
        });

        if (!usuario.getCpf().equals(usuarioDto.getCpf()))
            usuarioRepository.buscarPorCpf(usuarioDto.getCpf()).ifPresent(x -> {
                throw new RestException(HttpStatus.CONFLICT, "CPF já cadastrado");
            });

        if (!usuario.getEmail().equals(usuarioDto.getEmail()))
            usuarioRepository.buscarPorEmail(usuarioDto.getEmail()).ifPresent(x -> {
                throw new RestException(HttpStatus.CONFLICT, "E-mail já cadastrado");
            });

        usuario.setCpf(usuarioDto.getCpf());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setDataNascimento(usuarioDto.getDataNascimento());
        usuario.setNome(usuarioDto.getNome());

        return UsuarioDto.fromEntity(usuario);
    }

    @Override
    public void desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            throw new RestException(HttpStatus.NOT_FOUND, USUARIO_NAO_ENCONTRADO);
        });
        usuario.setAtivo(false);
    }
}
