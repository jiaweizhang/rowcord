package rowcord.models.requests;

import java.util.List;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class InviteUserRequest {
    public long groupId;
    public List<Long> userIds;

    public InviteUserRequest() {
    }

    public InviteUserRequest(long groupId, List<Long> userIds) {
        this.groupId = groupId;
        this.userIds = userIds;
    }
}
