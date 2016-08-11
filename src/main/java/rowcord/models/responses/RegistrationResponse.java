package rowcord.models.responses;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class RegistrationResponse extends StdResponse {
    public long userId;

    public RegistrationResponse(int status, boolean success, String message, long userId) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.userId = userId;
    }
}
