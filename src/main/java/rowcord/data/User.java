package rowcord.data;

/**
 * Created by jiaweizhang on 1/22/16.
 */
public class User {
    private int userId;
    private String passhash;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }
}
