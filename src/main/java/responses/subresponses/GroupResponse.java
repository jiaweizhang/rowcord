package responses.subresponses;

import java.sql.Date;

/**
 * Created by jiawe on 1/18/2016.
 */
public class GroupResponse {
    private String groupName;
    private String groupDescription;
    private Date createDate;

    public GroupResponse(String groupName, String groupDescription, Date createDate) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.createDate = createDate;
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
}
