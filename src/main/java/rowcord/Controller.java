package rowcord;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import rowcord.models.StdResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class Controller {

    /**
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public StdResponse exception(Exception ex, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new StdResponse("Bad", "Internal server error");
    }

}
