package requestdata.group;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class GroupApplicationRequest {
    private Integer groupId;

    public boolean isValid() {
        return groupId != null;
    }

    public int getGroupId() {
        return groupId;
    }
}
