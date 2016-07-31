package rowcord.models.requests;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class InviteUserRequest extends StdRequest {
    public long groupId;
    public List<Long> userIds;

    public InviteUserRequest() {
    }

    public InviteUserRequest(long groupId, List<Long> userIds) {
        this.groupId = groupId;
        this.userIds = userIds;
    }

    public void validate() {
        if (groupId < 1) {
            throw new IllegalArgumentException("groupId cannot be less than 1");
        }
        if (!userIds.stream().allMatch(new HashSet<>()::add)) {
            // duplicates exist
            throw new IllegalArgumentException("userIds cannot contains duplicates");
        }
    }
}
