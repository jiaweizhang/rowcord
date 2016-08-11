package rowcord.models.responses;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class GroupCreationResponse extends StdResponse {

    public long groupId;

    public GroupCreationResponse(int status, boolean success, String message, long groupId) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.groupId = groupId;
    }
}
