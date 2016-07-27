package rowcord.models.requests;

/**
 * Created by jiaweizhang on 7/26/2016.
 */
public class RegistrationRequest {
    public String email;
    public String password;

    public RegistrationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
