package rowcord.controllers;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import rowcord.models.responses.StdResponse;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

class Controller {

    ResponseEntity wrap(StdResponse stdResponse) {
        if (stdResponse.status.equals("Bad")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stdResponse);
        }
        return ResponseEntity.ok(stdResponse);
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseBody
    public StdResponse psqlException(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new StdResponse("Bad", "A database error has occurred. The error has been logged and will be forwarded to the development team.");
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public StdResponse sqlException(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new StdResponse("Bad", "A database error has occurred. The error has been logged and will be forwarded to the development team.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public StdResponse dataIntegrityViolationException(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new StdResponse("Bad", "A database error has occurred. The error has been logged and will be forwarded to the development team.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public StdResponse exception(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new StdResponse("Bad", "Generic internal server error: " + ex.getLocalizedMessage());
    }

}
