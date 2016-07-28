package rowcord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jiaweizhang on 7/28/2016.
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Jwt auth failed")
public class JwtAuthException extends RuntimeException {
}
