package responses.subresponses;

import java.sql.Date;
import java.util.List;

/**
 * Created by jiawe on 1/18/2016.
 */
public class GroupDetailResponse {
    private String groupName;
    private String groupDescription;
    private Date createDate;
    private List<String> admins;
    private boolean isAdmin;

    public GroupDetailResponse(String groupName, String groupDescription, Date createDate, List<String> admins, boolean isAdmin) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.createDate = createDate;
        this.admins = admins;
        this.isAdmin = isAdmin;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
