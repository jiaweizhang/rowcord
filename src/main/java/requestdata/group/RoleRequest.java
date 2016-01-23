package requestdata.group;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class RoleRequest {
    private Integer userId;
    private Integer groupId;
    private Boolean adminBool;
    private Boolean coachBool;

    public boolean isValid() {
        return userId != null && groupId != null && adminBool != null && coachBool != null;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Boolean getAdminBool() {
        return adminBool;
    }

    public Boolean getCoachBool() {
        return coachBool;
    }
}
