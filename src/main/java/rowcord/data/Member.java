package rowcord.data;

import java.util.Date;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class Member {
    private int userId;
    private String firstName;
    private String lastName;
    private boolean adminBool;
    private boolean coachBool;
    private Date joinDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
