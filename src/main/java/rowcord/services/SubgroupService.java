package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import requestdata.subgroup.AddToSubgroupRequest;
import requestdata.subgroup.CreateSubgroupRequest;
import responses.StandardResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaweizhang on 1/23/16.
 */


@Transactional
@Service
public class SubgroupService {

    @Autowired
    private JdbcTemplate jt;

    public StandardResponse createSubgroup(CreateSubgroupRequest req, int userId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, req.getGroupId(), userId);
        if (inGroup != 1) {
            return new StandardResponse(true, 3008, "You can't create subgroup because you're either not an admin or not in the group");
        }

        jt.update("INSERT INTO subgroups (group_id, subgroup_name, description) VALUES (?, ?, ?);",
                req.getGroupId(),
                req.getSubgroupName(),
                req.getDescription());

        return new StandardResponse(false, 0, "Successfully created subgroup");
    }

    public StandardResponse addToSubgroup(AddToSubgroupRequest req, int userId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, req.getGroupId(), userId);
        if (inGroup != 1) {
            return new StandardResponse(true, 3009, "You can't add to subgroup because you're either not an admin or not in the group");
        }

        List<Object[]> batch = new ArrayList<Object[]>();
        for (int user : req.getMembers()) {
            Object[] values = new Object[] {
                    req.getSubgroupId(),
                    user};
            batch.add(values);
        }
        int[] updateCounts = jt.batchUpdate(
                "INSERT INTO subgroupmembers (subgroup_id, user_id) VALUES (?, ?);",
                batch);
        System.out.println("number of users added to subgroupo: "+updateCounts);
        return new StandardResponse(false, 0, "successfully added members to subgroup");
    }

}
