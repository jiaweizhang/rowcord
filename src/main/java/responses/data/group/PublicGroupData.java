package responses.data.group;

import rowcord.data.PartGroup;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class PublicGroupData {
    private List<PartGroup> groups;

    public PublicGroupData(List<PartGroup> groups) {
        this.groups = groups;
    }

    public List<PartGroup> getGroups() {
        return groups;
    }
}
