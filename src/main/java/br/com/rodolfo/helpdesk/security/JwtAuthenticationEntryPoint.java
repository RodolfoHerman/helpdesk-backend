package br.com.rodolfo.helpdesk.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JwtAuthenticationEntryPoint Excessão padrão utilizado pelo Security
 * Responsável por retornar uma excessão se houver problemas na autenticação
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."
        );
	}

    
}