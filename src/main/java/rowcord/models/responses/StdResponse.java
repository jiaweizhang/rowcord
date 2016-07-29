package rowcord.models.responses;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class StdResponse {
    public int status;
    public boolean success;
    public String message;

    public StdResponse() {
    }

    public StdResponse(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}
