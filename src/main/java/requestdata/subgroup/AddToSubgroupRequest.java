package requestdata.subgroup;

import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class AddToSubgroupRequest {
    private Integer groupId;
    private Integer subgroupId;
    private List<Integer> members;

    public boolean isValid() {
        return groupId != null && subgroupId != null && members != null;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getSubgroupId() {
        return subgroupId;
    }

    public List<Integer> getMembers() {
        return members;
    }
}
