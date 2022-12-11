package example.skeleton.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;
import java.util.Date;

public class TokenParser {

    private TokenParser() {
    }

    private static final KeyPair keys = Keys.keyPairFor(SignatureAlgorithm.RS512);

    public static String generateToken() {

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // 30 minutes ...
                .signWith(keys.getPrivate())
                .compact();
    }

    public static boolean validateToken(String jwtToken) {
        try {

            if (jwtToken.startsWith("Bearer") || jwtToken.startsWith("bearer")) {
                jwtToken = jwtToken.replaceFirst("bearer ", "");
                jwtToken = jwtToken.replaceFirst("Bearer ", "");
            }

            Jwts.parserBuilder()
                    .setSigningKey(keys.getPublic())
                    .build()
                    .parseClaimsJws(jwtToken);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
