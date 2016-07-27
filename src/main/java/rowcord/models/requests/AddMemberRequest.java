package rowcord.models.requests;

import java.util.List;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class AddMemberRequest {
    public List<Long> members;
    public long groupId;

    public AddMemberRequest(long groupId, List<Long> members) {
        this.groupId = groupId;
        this.members = members;
    }
}
