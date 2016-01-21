package requestdata.auth;

/**
 * Created by jiawe on 1/17/2016.
 */
public class UserRequest {
    private String email;
    private char[] password;

    public String getEmail(){
        return email;
    }

    public char[] getPassword() {
        return password;
    }
}
