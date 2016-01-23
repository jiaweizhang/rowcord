package requestdata.subgroup;

/**
 * Created by jiaweizhang on 1/23/16.
 */
public class CreateSubgroupRequest {
    private Integer groupId;
    private String subgroupName;
    private String description;

    public boolean isValid() {
        return groupId != null && subgroupName != null && description != null;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getSubgroupName() {
        return subgroupName;
    }

    public String getDescription() {
        return description;
    }
}
