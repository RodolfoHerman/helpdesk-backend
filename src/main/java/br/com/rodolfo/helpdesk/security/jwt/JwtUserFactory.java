package br.com.rodolfo.helpdesk.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.rodolfo.helpdesk.enums.PerfilEnum;
import br.com.rodolfo.helpdesk.models.Usuario;

/**
 * JwtUserFactory
 */
public class JwtUserFactory {

    private JwtUserFactory() {}

    /**
     * Converter Usuario para um objeto JwtUser
     * @param usuario
     * @return JwtUser
     */
    public static JwtUser criar(Usuario usuario) {
        
        return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(), mapearAutorizacoes(usuario.getPerfil()));
    }

    /**
     * Mapear o perfil do usuário para o formato do SpringSecurity
     * @param perfilEnum
     * @return autorizações
     */
    private static List<GrantedAuthority> mapearAutorizacoes(PerfilEnum perfilEnum) {

        List<GrantedAuthority> autorizacoes = new ArrayList<>();
        autorizacoes.add(new SimpleGrantedAuthority(perfilEnum.toString()));

        return autorizacoes;
    }

}