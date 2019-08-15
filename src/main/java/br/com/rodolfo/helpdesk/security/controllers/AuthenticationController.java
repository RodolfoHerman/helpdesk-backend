package br.com.rodolfo.helpdesk.security.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.helpdesk.response.Response;
import br.com.rodolfo.helpdesk.security.dto.JwtAuthenticationDto;
import br.com.rodolfo.helpdesk.security.dto.TokenDto;
import br.com.rodolfo.helpdesk.security.utils.JwtTokenUtil;

/**
 * AuthenticationController
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private static final String TOKEN_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Gera e retorna um novo Token JWT
     * @param authenticationDto
     * @param result
     * @return ResponseEntity
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<Response<TokenDto>> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto, BindingResult result) throws AuthenticationException {
        
        Response<TokenDto> response = new Response<>();

        if(result.hasErrors()) {

            log.error("Erro na geração do token : {}", result.getAllErrors());
            result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        log.info("Gerando token para o email : {} ", authenticationDto.getEmail());

        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
        String token = jwtTokenUtil.obterToken(userDetails);
        response.setData(new TokenDto(token));

        return ResponseEntity.ok(response);
    }

    /**
     * Gerar um novo Token JWT com nova data de expiração.
     * @param request
     * @return ResponseEntity
     */
    @PostMapping(value = "/refresh")
    public ResponseEntity<Response<TokenDto>> gerarRefreshTokenJwt(HttpServletRequest request) {
        
        log.info("Gerando refresh Token JWT");
        Response<TokenDto> response = new Response<>();

        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if(token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {

            token = Optional.of(token.get().substring(7));
        }

        if(!token.isPresent()) {

            response.getErrors().add("Token não informado.");
        
        } else if(! jwtTokenUtil.isTokenValido(token.get())) {

            response.getErrors().add("Token Inválido ou Expirado");
        }

        if(!response.getErrors().isEmpty()) {

            return ResponseEntity.badRequest().body(response);
        }

        String refreshToken = jwtTokenUtil.refreshToken(token.get());
        response.setData(new TokenDto(refreshToken));

        return ResponseEntity.ok(response);
    }

}