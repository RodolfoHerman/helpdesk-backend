package br.com.rodolfo.helpdesk.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.security.JwtUserFactory;
import br.com.rodolfo.helpdesk.services.UsuarioService;

/**
 * JwtUserDetailsServiceImpl
 */
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.findByEmail(username);

        if(usuario != null) {

            return JwtUserFactory.create(usuario);
        }
        
        throw new UsernameNotFoundException("Email não encontrado");
	}

    
}