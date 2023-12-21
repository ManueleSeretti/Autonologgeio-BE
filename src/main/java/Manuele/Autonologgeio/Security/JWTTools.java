package Manuele.Autonologgeio.Security;

import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component// CREAZIONE DI FUNZIONE LEGATE ALL'AUTORIZZAZIONE
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(User user) {

        return Jwts.builder().setSubject(String.valueOf(user.getId()))// Subject <-- A chi appartiene il token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Data di scadenza (Expiration Date)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

    }

    public void verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }

    }

    public String extractIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();

    }
}
