package requestdata.auth;

/**
 * Created by jiaweizhang on 1/17/2016.
 */
public class UserRequest {
    private String email;
    private char[] password;

    public boolean isValid() {
        return email != null && password != null;
    }

    public String getEmail(){
        return email;
    }

    public char[] getPassword() {
        return password;
    }
}
