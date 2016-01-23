package responses;

/**
 * Created by jiaweizhang on 1/18/2016.
 */
public class StandardResponse {
    private boolean error;
    private int code;
    private String message;
    private Object data;

    public StandardResponse(boolean error, int code, String message, Object data) {
        this.error = error;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public StandardResponse(boolean error, int code, String message) {
        this(error, code, message, null);
    }

    public boolean isError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
