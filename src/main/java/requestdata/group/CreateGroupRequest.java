package requestdata.group;

/**
 * Created by jiaweizhang on 1/18/2016.
 */
public class CreateGroupRequest {
    private String groupName;
    private String description;
    private Boolean publicBool;

    public boolean isValid() {
        return groupName != null && description != null && publicBool != null;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getPublicBool() {
        return publicBool;
    }
}
