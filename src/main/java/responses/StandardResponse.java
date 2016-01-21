package responses;

/**
 * Created by jiawe on 1/18/2016.
 */
public class StandardResponse {
    private boolean is_error;
    private String message;
    private Object committed;
    private Object data;

    public StandardResponse(boolean is_error, String message, Object committed, Object data) {
        this.is_error = is_error;
        this.message = message;
        this.committed = committed;
        this.data = data;
    }

    public StandardResponse(boolean is_error, String message, Object committed) {
        this(is_error, message, committed, null);
    }

    public StandardResponse(boolean is_error, String message) {
        this(is_error, message, null, null);
    }

    public boolean is_error() {
        return is_error;
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
