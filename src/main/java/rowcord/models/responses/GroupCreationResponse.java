package rowcord.models.responses;

/**
 * Created by Jiawei on 7/26/2016.
 */
public class GroupCreationResponse extends StdResponse {

    public long groupId;

    public GroupCreationResponse(int status, boolean error, String message, long groupId) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.groupId = groupId;
    }
}
