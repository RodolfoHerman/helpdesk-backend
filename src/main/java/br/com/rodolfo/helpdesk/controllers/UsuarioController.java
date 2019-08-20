package br.com.rodolfo.helpdesk.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.response.Response;
import br.com.rodolfo.helpdesk.services.UsuarioService;
import br.com.rodolfo.helpdesk.utils.PasswordUtils;

/**
 * UsuarioController
 */
@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<Response<Usuario>> adicionar(@RequestBody Usuario usuario, BindingResult result) {
        
        Response<Usuario> response = new Response<>();

        try {

            validarEmail(usuario, result);
            
            if(result.hasErrors()) {
    
                result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }

            usuario.setSenha(PasswordUtils.gerarBCrypt(usuario.getSenha()));
            Usuario u = this.usuarioService.createOrUpdate(usuario);
            response.setData(u);

        } catch (Exception e) {
            
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    private ResponseEntity<Response<Usuario>> atualizar(@RequestBody Usuario usuario, BindingResult result) {

        Response<Usuario> response = new Response<>();

        try {

            validarEmail(usuario, result);
            validarId(usuario, result);

            if(result.hasErrors()) {

                result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);    
            }

            usuario.setSenha(PasswordUtils.gerarBCrypt(usuario.getSenha()));
            Usuario u = this.usuarioService.createOrUpdate(usuario);
            response.setData(u);

        } catch (Exception e) {
            
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<Response<Usuario>> buscarPeloId(@PathVariable("id") String id) {
        
        Response<Usuario> response = new Response<>();
        
        Usuario usuario = this.usuarioService.findById(id);

        if(usuario == null) {

            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(usuario);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<Response<String>> deletar(@PathVariable("id") String id) {

        Response<String> response = new Response<>();

        Usuario usuario = this.usuarioService.findById(id);

        if(usuario == null) {

            response.getErrors().add("Registro não encontrado id: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.usuarioService.delete(id);
        response.setData("Registro deletado");

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "{pag}/{qtd}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<Response<Page<Usuario>>> buscarTodos(@PathVariable("pag") int pag, @PathVariable("qtd") int qtd) {
        
        Response<Page<Usuario>> response = new Response<>();
        
        Page<Usuario> usuarios = this.usuarioService.findAll(pag, qtd);

        response.setData(usuarios);

        return ResponseEntity.ok().body(response);
    }


    private void validarId(Usuario usuario, BindingResult result) {

        if(usuario.getId() == null) {

            result.addError(new ObjectError("Usuario", "ID não informado!"));
        }

    }

    private void validarEmail(Usuario usuario, BindingResult result) {

        Optional.ofNullable(this.usuarioService.findByEmail(usuario.getEmail()))
            .ifPresent(usu -> result.addError(new ObjectError("Usuario", "Email já cadastrado no sistema!")));
    }
}