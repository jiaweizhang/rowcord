package responses.subresponses;

import java.sql.Date;

/**
 * Created by jiawe on 1/18/2016.
 */
public class ApplicationResponse {
    private String email;
    private String groupName;
    private Date applyDate;

    public ApplicationResponse(String email, String groupName, Date applyDate) {
        this.email = email;
        this.groupName = groupName;
        this.applyDate = applyDate;
    }

    public String getEmail() {
        return email;
    }

    public String getGroupName() {
        return groupName;
    }

    public Date getApplyDate() {
        return applyDate;
    }
}
