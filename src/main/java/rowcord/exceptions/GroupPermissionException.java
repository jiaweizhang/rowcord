package rowcord.exceptions;

/**
 * Created by Jiawei on 7/30/2016.
 */
public class GroupPermissionException extends RuntimeException {
    public String message;

    public GroupPermissionException(String message) {
        this.message = message;
    }
}
