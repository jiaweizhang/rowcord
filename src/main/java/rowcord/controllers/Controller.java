package rowcord.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rowcord.models.requests.StdRequest;
import rowcord.models.responses.StdResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@org.springframework.stereotype.Controller
class Controller {

    void pre(StdRequest stdRequest, HttpServletRequest httpServletRequest) {
        String jwt = httpServletRequest.getHeader("Authorization");
        Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwt).getBody();
        stdRequest.userId = Long.parseLong(claims.getSubject());
    }

    ResponseEntity wrap(StdResponse stdResponse) {
        switch (stdResponse.status) {
            case "Ok":
                return ResponseEntity.status(HttpStatus.OK).body(stdResponse);
            case "Bad":
                return ResponseEntity.status(HttpStatus.OK).body(stdResponse);
            case "Internal":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stdResponse);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stdResponse);
        }
    }

    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Data integrity violation")  // 409
    @ExceptionHandler(IllegalArgumentException.class)
    public void conflict() {
        // Nothing to do
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleError(HttpServletRequest req, Exception ex) {
        return "something: "+ex.toString();
    }
}
