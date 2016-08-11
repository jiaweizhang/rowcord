package rowcord.models;

/**
 * Created by Jiawei on 7/30/2016.
 */
public class GroupPermissions {
    public long groupPermissions;
    public boolean isValid;
    public String message;

    public GroupPermissions(long groupPermissions, boolean isValid, String message) {
        this.groupPermissions = groupPermissions;
        this.isValid = isValid;
        this.message = message;
    }

    public boolean canModifyAnyUsersGroupPermissions() {
        return (groupPermissions & 1) != 0;
    }

    public boolean canInvite() {
        return (groupPermissions >> 1 & 1) != 0;
    }

    public boolean canChangeGroupDetail() {
        return (groupPermissions >> 2 & 1) != 0;
    }

    public boolean canNotifyGroup() {
        return (groupPermissions >> 3 & 1) != 0;
    }

    public boolean canCreateSubgroup() {
        return (groupPermissions >> 4 & 1) != 0;
    }

    public boolean canKickMember() {
        return (groupPermissions >> 5 & 1) != 0;
    }

    public boolean canDeleteSubgroup() {
        return (groupPermissions >> 6 & 1) != 0;
    }
}
