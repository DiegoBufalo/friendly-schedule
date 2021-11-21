package db.dbspringtemplate.rest;

import db.dbspringtemplate.dto.UsuarioDto;
import db.dbspringtemplate.error.RestErrorModel;
import db.dbspringtemplate.service.usuario.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioRestService {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioRestService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Buscar todos usuarios")
    public List<UsuarioDto> buscarUsuarios() {
        return this.usuarioService.buscarUsuarios();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Buscar usuario por id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public UsuarioDto buscarUsuario(@PathVariable Long id) {
        return this.usuarioService.buscarUsuario(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cria novo Usuario")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class),
            @ApiResponse(code = 409, message = "Conflict", response = RestErrorModel.class)
    })
    public UsuarioDto criarUsuario(@RequestBody @Valid UsuarioDto request) {
        return this.usuarioService.criarUsuario(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Atuliza usuário existente")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class),
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class),
            @ApiResponse(code = 409, message = "Conflict", response = RestErrorModel.class)
    })
    public UsuarioDto atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDto request) {
        return this.usuarioService.atualizarUsuario(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Inativa um usuário existente")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public void desativarUsuario(@PathVariable Long id) {
            this.usuarioService.desativarUsuario(id);
    }
}
