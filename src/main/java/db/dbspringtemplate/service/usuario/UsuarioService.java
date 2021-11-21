package db.dbspringtemplate.service.usuario;

import db.dbspringtemplate.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    UsuarioDto buscarUsuario(Long id);
    List<UsuarioDto> buscarUsuarios();
    UsuarioDto criarUsuario(UsuarioDto usuarioDto);
    UsuarioDto atualizarUsuario(Long id, UsuarioDto usuarioDto);
    void desativarUsuario(Long id);
}
