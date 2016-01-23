package responses.data.group;

import rowcord.data.MembershipGroup;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class MembershipGroupData {
    private List<MembershipGroup> groups;

    public MembershipGroupData(List<MembershipGroup> groups) {
        this.groups = groups;
    }

    public List<MembershipGroup> getGroups() {
        return groups;
    }
}
