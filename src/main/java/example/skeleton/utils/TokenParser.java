package example.skeleton.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;
import java.util.Date;

import static example.skeleton.constant.Constants.expiredUserTokens;

public class TokenParser {

    private TokenParser() {
    }

    private static final KeyPair keys = Keys.keyPairFor(SignatureAlgorithm.RS512);

    public static String generateToken(String username) {

        return Jwts.builder()
                .setIssuer(username)
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

            return !expiredUserTokens.contains(jwtToken);
        } catch (Exception e) {
            return false;
        }
    }

    public static void invalidateToken(String userToken) {
        expiredUserTokens.add(userToken);
    }
}
