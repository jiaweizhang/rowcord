package rowcord.data;

import java.util.Date;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class MembershipGroup {
    private int groupId;
    private String groupName;
    private Date joinDate;
    private boolean adminBool;
    private boolean coachBool;

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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isAdminBool() {
        return adminBool;
    }

    public void setAdminBool(boolean adminBool) {
        this.adminBool = adminBool;
    }

    public boolean isCoachBool() {
        return coachBool;
    }

    public void setCoachBool(boolean coachBool) {
        this.coachBool = coachBool;
    }
}
