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
import rowcord.exceptions.GroupPermissionException;
import rowcord.exceptions.JwtAuthException;
import rowcord.models.responses.StdResponse;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends Controller {

    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity handleError() {
        return wrap(new StdResponse(403, false, "Jwt Auth failed"));
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity handleSqlException(Exception e) {
        return wrap(new StdResponse(500, false, "Database error: " + e.getLocalizedMessage()));
    }

    @ExceptionHandler(GroupPermissionException.class)
    public ResponseEntity handleSqlException(GroupPermissionException e) {
        return wrap(new StdResponse(500, false, "Group permissions not valid: " + e.message));
    }
}