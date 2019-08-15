package br.com.rodolfo.helpdesk.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtTokenUtil
 * Realiza todo os controles do Token
 */
@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Retorna o usuário (email) contido no Token JWT
     * @param token
     * @return String
     */
    public String getUserNameFromToken(String token) {

        String username;

        try {
            
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();

        } catch (Exception e) {
            
            username = null;
        }

        return username;
    }

    /**
     * Retorna a data de expiração de um Token JWT
     * @param token
     * @return String
     */
    private Date getExpirationDateFromToken(String token) {
        
        Date expiration;

        try {
            
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();

        } catch (Exception e) {
            
            expiration = null;
        }

        return expiration;
    }

    /**
     * Criar um novo Token JWT
     * @param token
     * @return String
     */
    public String refreshToken(String token) {
        
        String refreshedToken;

        try {
            
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = gerarToken(claims);

        } catch (Exception e) {
            
            refreshedToken = null;
        }

        return refreshedToken;
    }

    /**
     * Verifica se o Token JWT é válido
     * @param token
     * @return boolean
     */
    public boolean isTokenValido(String token) {
        
        return !isTokenExpirado(token);
    }

    /**
     * Retorna novo Token JWT com base nos dados do usuário
     * @param userDetails
     * @return String
     */
    public String obterToken(UserDetails userDetails) {
        
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
        claims.put(CLAIM_KEY_CREATED, new Date());

        return gerarToken(claims);
    }

    /**
     * Realiza o parse do Token JWT, extraindo as informações em seu corpo
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        
        Claims claims;

        try {
            
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            
            claims = null;
        }

        return claims;
    }

    /**
     * Retorna a nova data de expiração com base na data atual
     * @return Date
     */
    private Date gerarDataExpiracao() {
        
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }
    
    /**
     * Verifica se um Token JWT está expirado
     * @param token
     * @return boolean
     */
    private boolean isTokenExpirado(String token) {
        
        Date dataExpiracao = getExpirationDateFromToken(token);

        if(dataExpiracao == null) {

            return false;
        }

        return dataExpiracao.before(new Date());
    }


    /**
     * Gerar Token JWT com os dados fornecidos no claims
     * @param claims
     * @return String
     */
    private String gerarToken(Map<String, Object> claims) {
        
        return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
}