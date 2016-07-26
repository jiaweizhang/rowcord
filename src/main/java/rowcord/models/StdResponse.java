package rowcord.models;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class StdResponse {
    public String status;
    public String message;

    public StdResponse() {
    }

    public StdResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
