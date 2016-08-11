package rowcord.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rowcord.exceptions.JwtAuthException;
import rowcord.models.requests.StdRequest;
import rowcord.models.responses.StdResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

public class Controller {

    void pre(StdRequest stdRequest, HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization");
        try {
            Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwt).getBody();
            stdRequest.userId = Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new JwtAuthException();
        }
    }

    protected ResponseEntity wrap(StdResponse stdResponse) {
        stdResponse.timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        switch (stdResponse.status) {
            case 200:
                return ResponseEntity.status(HttpStatus.OK).body(stdResponse);
            case 403:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(stdResponse);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stdResponse);
            default:
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(stdResponse);
        }
    }
}
