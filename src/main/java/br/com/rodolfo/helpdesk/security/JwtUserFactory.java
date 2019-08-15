package br.com.rodolfo.helpdesk.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.rodolfo.helpdesk.enums.PerfilEnum;
import br.com.rodolfo.helpdesk.models.Usuario;

/**
 * JwtUserFactory
 * Converte o usuário para o tipo JwtUser
 */
public class JwtUserFactory {

    /**
     * Não permitir instância
     */
    private JwtUserFactory() {}

    /**
     * Converte o tipo Usuario em JwtUser e retorna com os dados do usuário
     * @param usuario
     * @return JwtUser
     */
    public static JwtUser create(Usuario usuario) {

        return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
                mapToGrantedAuthorities(usuario.getPerfil()));
    }

    /**
     * Converte o perfil do usuário no formato utilizado pelo Security
     * @param perfil
     * @return
     */
	private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil) {
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(perfil.toString()));

        return authorities;
	}
    
}