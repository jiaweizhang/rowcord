package rowcord.services;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.responses.GroupCreationResponse;
import rowcord.models.responses.StdResponse;

import java.sql.PreparedStatement;

/**
 * Created by Jiawei on 7/26/2016.
 */

@org.springframework.stereotype.Service
@Transactional
public class GroupService extends Service {

    public StdResponse createGroup(GroupCreationRequest groupCreationRequest) {
        int groupNameCount = this.jt.queryForObject(
                "SELECT COUNT(*) FROM groups WHERE groupName = ?",
                new Object[]{groupCreationRequest.groupName}, Integer.class);
        if (groupNameCount != 0) {
            return new StdResponse("Bad", "Group name already exists");
        }

        if (groupCreationRequest.groupTypeId < 1 || groupCreationRequest.groupTypeId > 3) {
            return new StdResponse("Bad", "Group type not valid");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jt.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO groups (groupName, groupDescription, groupTypeId) VALUES (?, ?, ?)",
                            new String[]{"groupid"});
                    ps.setString(1, groupCreationRequest.groupName);
                    ps.setString(2, groupCreationRequest.groupDescription);
                    ps.setInt(3, groupCreationRequest.groupTypeId);
                    return ps;
                },
                keyHolder);

        return new GroupCreationResponse("Ok", "Successfully created group", keyHolder.getKey().longValue());
    }
}