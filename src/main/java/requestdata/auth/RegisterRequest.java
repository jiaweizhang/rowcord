package requestdata.auth;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class RegisterRequest {
    private String email;
    private char[] password;
    private String firstName;
    private String lastName;

    public boolean isValid() {
        return email != null && password != null && firstName != null && lastName != null;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
