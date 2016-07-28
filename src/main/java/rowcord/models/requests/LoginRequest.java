package rowcord.models.requests;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class LoginRequest {
    public String email;
    public String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
