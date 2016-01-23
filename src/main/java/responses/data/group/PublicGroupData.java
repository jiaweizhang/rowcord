package responses.data.group;

import rowcord.data.PublicGroup;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class PublicGroupData {
    private List<PublicGroup> groups;

    public PublicGroupData(List<PublicGroup> groups) {
        this.groups = groups;
    }

    public List<PublicGroup> getGroups() {
        return groups;
    }
}
