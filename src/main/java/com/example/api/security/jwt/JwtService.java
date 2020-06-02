package com.example.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.subscription-key}")
    private String subscriptionKey;

    public String generedToken(User user){
        long expString = Long.valueOf(expiration);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(expString);
        Instant instant = expirationTime.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, subscriptionKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(subscriptionKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Verifica se o Token esta valido, se passou os 30 minutos o token será invalido.
     * @param token
     * @return
     */
    public boolean tokenValid(String token){
        try{
            Claims claims = getClaims(token);
            Date dateExpiration = claims.getExpiration();
            LocalDateTime date =
                    dateExpiration.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return  !LocalDateTime.now().isAfter(date);
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Busca no claims o usuario logado
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public String getLoginUser(String token) throws ExpiredJwtException{
        return (String) getClaims(token).getSubject();
    }
}
