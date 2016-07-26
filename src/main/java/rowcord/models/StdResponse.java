package rowcord.models;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class StdResponse {
    public String status;
    public String message;
    public Object data;

    public StdResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public StdResponse(String status, String message) {
        this(status, message, null);
    }
}
