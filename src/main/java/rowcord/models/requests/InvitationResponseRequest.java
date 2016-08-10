package rowcord.models.requests;

/**
 * Created by Jiawei on 8/9/2016.
 */
public class InvitationResponseRequest extends StdRequest {
    public long userId;
    public long groupId;
    public boolean accept;
}
