package rowcord.models.responses;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class StdResponse {
    public int status;
    public String message;

    public StdResponse() {
    }

    public StdResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
