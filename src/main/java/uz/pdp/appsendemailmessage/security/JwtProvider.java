package uz.pdp.appsendemailmessage.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appsendemailmessage.entity.RoleEntity;

import java.util.Date;
import java.util.Set;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@Component
public class JwtProvider {

    private static final String secretKay = "TOKENUCHUNKOD";
    private static final long expirationDate = 10000000;

    public String generationToken(String username, Set<RoleEntity> roleEntities) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationDate))
                .claim("roles", roleEntities)
                .signWith(SignatureAlgorithm.HS512, secretKay)
                .compact();

    }

    public String validateToken(String authToken) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKay)
                    .parseClaimsJws(authToken)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
