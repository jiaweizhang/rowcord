package rowcord.models.responses;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class StdResponse {
    public int status;
    public boolean error;
    public String message;

    public StdResponse() {
    }

    public StdResponse(int status, boolean error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
