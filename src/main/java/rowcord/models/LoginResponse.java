package rowcord.models;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class LoginResponse extends StdResponse {
    public String jwt;

    public LoginResponse(String status, String message, String jwt) {
        this.status = status;
        this.message = message;
        this.jwt = jwt;
    }
}
