package utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * Created by jiaweizhang on 1/17/2016.
 */
public class TokenCreator {
    public static String generateToken(int userId) {
        String jwt = Jwts.builder()
                .setSubject(Integer.toString(userId))
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        return jwt;
    }
}
