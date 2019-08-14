package br.com.rodolfo.helpdesk.security.jwt;

import java.io.Serializable;
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
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2702644991065289158L;

    private String CLAIM_KEY_USERNAME = "sub";
    private String CLAIM_KEY_ROLE = "role";
    private String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    // @Value("${expiration}")
    private Long expiration = 604800L;


    /**
     * Extrair o username (email neste caso) do Token
     * @param token
     * @return username (email)
     */
    public String extrairUsernameDoToken(String token) {

        String username;

        try {
            
            Claims claims = this.getClaimsDoToken(token);
            username = claims.getSubject();

        } catch (Exception e) {
            
            username = null;
        }

        return username;
    }

    /**
     * Extrair a data de expiração do Token
     * @param token
     * @return data de expiração
     */
    public Date extrairDataExpiracaoDoToken(String token) {
        
        Date expiracao;

        try {

            Claims claims = this.getClaimsDoToken(token);
            expiracao = claims.getExpiration();
            
        } catch (Exception e) {
            
            expiracao = null;
        }

        return expiracao;
    }

    /**
     * Realiza o parser do Token JWT para extrair as informações contidas no corpo dele.
     * @param token
     * @return claims (informações)
     */
    public Claims getClaimsDoToken(String token) {
        
        Claims claims;

        try {
            
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            
            claims = null;
        }

        return claims;
    }

    /**
     * Verificar se o Token está expirado
     * @param token
     * @return
     */
    public boolean isTokenExpirado(String token) {
        
        Date dataExpiracao = null;

        if(dataExpiracao == null) {

            return false;
        }

        return dataExpiracao.before(new Date());
    }

    /**
     * Responsável por gerar o Token
     * @param userDetails
     * @return token
     */
    public String gerarNovoToken(UserDetails userDetails) {
        
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        return this.gerarToken(claims);
    }

    /**
     * Gerar Token JWT com as informações (claims) fornecidas
     * @param claims
     * @return token
     */
    public String gerarToken(Map<String, Object> claims) {
        
        final Date dataCriacao   = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date dataExpicarao = new Date(dataCriacao.getTime() + this.expiration * 1000);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(dataExpicarao)
            .signWith(SignatureAlgorithm.HS512, this.secret)
            .compact();
    }

    /**
     * Verifica se o Token pode ser atualizado
     * @param token
     * @return
     */
    public boolean isTokenPodeSerAtualizado(String token) {
        return (!this.isTokenExpirado(token));
    }

    /**
     * Atualizar o Token
     * @param token
     * @return
     */
    public String atualizarToken(String token) {
        
        String tokenAtualizado;

        try {
            
            final Claims claims = getClaimsDoToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            tokenAtualizado = gerarToken(claims);

        } catch (Exception e) {
            
            tokenAtualizado = null;
        }

        return tokenAtualizado;
    }

    /**
     * Verificar se o Token é válido
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValido(String token, UserDetails userDetails) {
        
        JwtUser usuario = (JwtUser) userDetails;

        final String userName = extrairUsernameDoToken(token);

        return (userName.equals(usuario.getUsername()) && (!isTokenExpirado(token)));
    }
}