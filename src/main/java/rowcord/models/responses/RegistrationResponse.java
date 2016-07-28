package rowcord.models.responses;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class RegistrationResponse extends StdResponse {
    public long userId;

    public RegistrationResponse(int status, boolean error, String message, long userId) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.userId = userId;
    }
}
