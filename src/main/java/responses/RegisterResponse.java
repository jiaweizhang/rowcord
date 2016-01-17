package responses;

/**
 * Created by jiawe on 1/17/2016.
 */
public class RegisterResponse {
    private String status = "";
    private String message = "";

    public RegisterResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
