package br.com.rodolfo.helpdesk.security.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.security.jwt.JwtAuthenticationRequest;
import br.com.rodolfo.helpdesk.security.jwt.JwtTokenUtil;
import br.com.rodolfo.helpdesk.security.models.CurrentUser;
import br.com.rodolfo.helpdesk.services.UsuarioService;

/**
 * AuthenticationRestController
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/api/auth")
    public ResponseEntity<?> criarTokenDeAutenticacao(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        
        final Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.gerarNovoToken(userDetails);
        final Usuario usuario = this.usuarioService.findByEmail(authenticationRequest.getEmail());
        usuario.setSenha(null);

        return ResponseEntity.ok(new CurrentUser(token, usuario));
    }

    @PostMapping(value = "/api/refresh")
    public ResponseEntity<?> atualizarEGetAuthenticationToken(HttpServletRequest request) {
        
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.extrairUsernameDoToken(token);
        final Usuario usuario = this.usuarioService.findByEmail(username);

        if(jwtTokenUtil.isTokenPodeSerAtualizado(token)) {

            String tokenAtualizado = jwtTokenUtil.atualizarToken(token);
            return ResponseEntity.ok(new CurrentUser(tokenAtualizado, usuario));

        } else {

            return ResponseEntity.badRequest().body(null);
        }
    }
}