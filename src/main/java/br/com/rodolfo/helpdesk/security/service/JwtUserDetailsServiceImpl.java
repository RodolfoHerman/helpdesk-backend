package br.com.rodolfo.helpdesk.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.security.jwt.JwtUserFactory;
import br.com.rodolfo.helpdesk.services.UsuarioService;

/**
 * JwtUserDetailsServiceImpl
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = this.usuarioService.findByEmail(email);

        if(usuario == null) {

            throw new UsernameNotFoundException(String.format("nenhum usuário encontrado com o email: '%s'.", email));
        }

        return JwtUserFactory.criar(usuario);
	}

    
}