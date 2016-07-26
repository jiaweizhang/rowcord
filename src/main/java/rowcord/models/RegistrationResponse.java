package rowcord.models;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class RegistrationResponse extends StdResponse {
    public long userId;
    public String jwt;

    public RegistrationResponse(String status, String message, String jwt) {
        this.status = status;
        this.message = message;
        this.jwt = jwt;
    }
}
