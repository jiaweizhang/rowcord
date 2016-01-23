package rowcord.data;

import java.util.Date;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class Member {
    private int userId;
    private boolean adminBool;
    private boolean coachBool;
    private Date joinDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
