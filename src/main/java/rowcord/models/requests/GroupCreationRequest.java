package rowcord.models.requests;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class GroupCreationRequest extends StdRequest{
    public String groupName;
    public String groupDescription;
    public int groupTypeId;

    public GroupCreationRequest() {
    }

    public GroupCreationRequest(String groupName, String groupDescription, int groupTypeId) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupTypeId = groupTypeId;
    }
}
