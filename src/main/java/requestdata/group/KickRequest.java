package requestdata.group;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class KickRequest {
    private Integer userId;
    private Integer groupId;

    public boolean isValid() {
        return userId != null && groupId != null;
    }

    public int getUserId() {
        return userId;
    }

    public int getGroupId() {
        return groupId;
    }
}
