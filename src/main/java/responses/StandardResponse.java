package responses;

/**
 * Created by jiaweizhang on 1/18/2016.
 */
public class StandardResponse {
    private boolean error;
    private String message;
    private Object committed;
    private Object data;

    public StandardResponse(boolean error, String message, Object committed, Object data) {
        this.error = error;
        this.message = message;
        this.committed = committed;
        this.data = data;
    }

    public StandardResponse(boolean error, String message, Object committed) {
        this(error, message, committed, null);
    }

    public StandardResponse(boolean error, String message) {
        this(error, message, null, null);
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Object getCommitted() {
        return committed;
    }

    public Object getData() {
        return data;
    }
}
