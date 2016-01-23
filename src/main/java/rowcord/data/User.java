package rowcord.data;

/**
 * Created by jiaweizhang on 1/22/16.
 */
public class User {
        private int user_id;
        private String passhash;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }
}
