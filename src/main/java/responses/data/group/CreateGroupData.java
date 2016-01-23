package responses.data.group;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class CreateGroupData {
    private int groupId;
    private String groupName;
    private String description;
    private boolean publicBool;

    public CreateGroupData(int groupId, String groupName, String description, boolean publicBool) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.publicBool = publicBool;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublicBool() {
        return publicBool;
    }
}
