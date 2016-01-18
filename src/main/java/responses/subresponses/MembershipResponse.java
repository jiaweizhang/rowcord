package responses.subresponses;

/**
 * Created by jiawe on 1/18/2016.
 */
public class MembershipResponse {
    private String groupName;
    private int admin;
    private int coach;

    public MembershipResponse(String groupName, int admin, int coach) {
        this.groupName = groupName;
        this.admin = admin;
        this.coach = coach;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getAdmin() {
        return admin;
    }

    public int getCoach() {
        return coach;
    }
}
