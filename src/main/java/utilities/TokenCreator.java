package utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by jiawe on 1/17/2016.
 */
public class TokenCreator {
    public static String generateToken(String email, List<String> roleList) {
        LocalDate now = LocalDate.now();
        Date nowDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate expire = LocalDate.now().plusDays(7);
        Date expireDate = Date.from(expire.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String jwt = Jwts.builder().setSubject(email)
                .claim("roles", roleList).setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        return jwt;
    }
}
