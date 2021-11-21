package db.dbspringtemplate.rest;

import db.dbspringtemplate.dto.AgendaUsuarioDto;
import db.dbspringtemplate.error.RestErrorModel;
import db.dbspringtemplate.service.agendausuario.AgendaUsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/usuario/{usuarioId}/agenda")
public class AgendaUsuarioRestService {

    private final AgendaUsuarioService agendaUsuarioService;

    @Autowired
    public AgendaUsuarioRestService(AgendaUsuarioService agendaUsuarioService) {
        this.agendaUsuarioService = agendaUsuarioService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Buscar agenda do usuario")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public List<AgendaUsuarioDto> buscarAgendaUsuario(@PathVariable Long usuarioId) {
        return this.agendaUsuarioService.buscarAgendaUsuario(usuarioId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Inserir item na agenda")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class),
            @ApiResponse(code = 409, message = "Conflict", response = RestErrorModel.class)
    })
    public AgendaUsuarioDto inserirItemNaAgenda(@PathVariable Long usuarioId,
                                    @RequestBody @Valid AgendaUsuarioDto request) {
       return this.agendaUsuarioService.inserirItemNaAgenda(usuarioId, request);
    }

    @PutMapping("/{diasAmaterializar}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Rematerializa agenda do usuario inserindo novos dias")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request", response = RestErrorModel.class)
    })
    public void rematerializarAgendaUsuario(@PathVariable Long usuarioId,
                                                  @PathVariable Integer diasAmaterializar) {

        this.agendaUsuarioService.rematerializarAgendaUsuario(usuarioId, diasAmaterializar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Limpar a agenda")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Sample not found", response = RestErrorModel.class)
    })
    public void desativarUsuario(@PathVariable Long usuarioId) {
            this.agendaUsuarioService.limparAgenda(usuarioId);
    }
}
