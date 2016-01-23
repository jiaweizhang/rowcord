package rowcord.data;

import java.util.Date;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class ByIdGroup {
    private int groupId;
    private String groupName;
    private String description;
    private boolean publicBool;
    private Date createDate;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublicBool() {
        return publicBool;
    }

    public void setPublicBool(boolean publicBool) {
        this.publicBool = publicBool;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
