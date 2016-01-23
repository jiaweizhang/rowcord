package responses.data.group;

import rowcord.data.ByIdGroup;
import rowcord.data.Member;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class ByIdGroupData {
    private ByIdGroup group;
    private List<Member> members;

    public ByIdGroupData(ByIdGroup group, List<Member> members) {
        this.group = group;
        this.members = members;
    }

    public ByIdGroup getGroup() {
        return group;
    }

    public List<Member> getMembers() {
        return members;
    }
}
