package responses;

/**
 * Created by jiawe on 1/18/2016.
 */
public class StandardResponse {
    private String status;
    private Object data;
    private String message;

    public StandardResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public StandardResponse(String status, String message) {
        this(status, message, null);
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
