package rowcord;

/**
 * Created by jiaweizhang on 7/28/2016.
 */

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import rowcord.controllers.Controller;
import rowcord.exceptions.JwtAuthException;
import rowcord.models.responses.StdResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends Controller {

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity handleError() {
        return wrap(new StdResponse(403, true, "Jwt Auth failed"));
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity handleSqlException() {
        return wrap(new StdResponse(500, true, "Database error"));
    }
}