package responses;

/**
 * Created by jiawe on 1/17/2016.
 */
public class LoginResponse {
    private String status = "";
    private String message = "";
    private String token = "";

    public LoginResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
