package rowcord.services;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.transaction.annotation.Transactional;
import rowcord.exceptions.GroupPermissionException;
import rowcord.models.GroupPermissions;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.requests.GroupSearchRequest;
import rowcord.models.requests.InviteUserRequest;
import rowcord.models.responses.GroupCreationResponse;
import rowcord.models.responses.GroupSearchResponse;
import rowcord.models.responses.StdResponse;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Jiawei on 7/26/2016.
 */

@org.springframework.stereotype.Service
@Transactional
public class GroupService extends Service {

    public StdResponse createGroup(GroupCreationRequest groupCreationRequest) {

        List<SqlParameter> paramList = Arrays.asList(
                new SqlParameter("p_groupName", Types.VARCHAR),
                new SqlParameter("p_groupDescription", Types.VARCHAR),
                new SqlParameter("p_groupTypeId", Types.INTEGER),
                new SqlParameter("p_userId", Types.BIGINT),
                new SqlOutParameter("p_groupId", Types.BIGINT),
                new SqlOutParameter("p_success", Types.BOOLEAN),
                new SqlOutParameter("p_message", Types.VARCHAR)
        );

        final String procedureCall = "{call sp_createGroup(?, ?, ?, ?, ?, ?, ?)}";
        Map<String, Object> resultMap = this.jt.call(connection -> {

            CallableStatement callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1, groupCreationRequest.groupName);
            callableStatement.setString(2, groupCreationRequest.groupDescription);
            callableStatement.setInt(3, groupCreationRequest.groupTypeId);
            callableStatement.setLong(4, groupCreationRequest.userId);
            callableStatement.registerOutParameter(5, Types.BIGINT);
            callableStatement.registerOutParameter(6, Types.BOOLEAN);
            callableStatement.registerOutParameter(7, Types.VARCHAR);
            return callableStatement;

        }, paramList);

        if ((boolean) resultMap.get("p_success")) {
            return new GroupCreationResponse(
                    200,
                    true,
                    (String) resultMap.get("p_message"),
                    (long) resultMap.get("p_groupId")
            );
        }
        return new StdResponse(200, false, (String) resultMap.get("p_message"));
    }

    public StdResponse inviteUsers(InviteUserRequest req) {
        req.validate();

        GroupPermissions groupPermissions = getGroupPermissionsForUser(req.userId, req.groupId);
        if (!groupPermissions.isValid) {
            throw new GroupPermissionException(groupPermissions.message);
        }

        for (long m : req.userIds) {
            /*
            if (!userExists(m)) {
                return new StdResponse(200, false, "User " + m + " does not exist");
            }*/
            if (memberIsInGroup(m, req.groupId)) {
                return new StdResponse(200, false, "User " + m + " is already in the group");
            }
            if (memberHasInvitation(m, req.groupId)) {
                return new StdResponse(200, false, "User " + m + " is already has an invitation");
            }
        }

        List<Object[]> batch = new ArrayList<Object[]>();
        for (long m : req.userIds) {
            batch.add(new Object[]{m, req.groupId});
        }
        int[] updateCounts = this.jt.batchUpdate("INSERT INTO groupInvitations (userId, groupId) VALUES (?, ?)", batch);
        long totalUpdateCounts = IntStream.of(updateCounts).sum();
        return new StdResponse(200, true, totalUpdateCounts + " userIds added to group");
    }

    private boolean memberIsInGroup(long userId, long groupId) {
        boolean isInGroup = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groupMembers WHERE userId = ? AND groupId = ?)", new Object[]{userId, groupId}, Boolean.class);
        return isInGroup;
    }

    private boolean memberHasInvitation(long userId, long groupId) {
        boolean hasInvitation = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groupInvitations WHERE userId = ? AND groupId = ?)", new Object[]{userId, groupId}, Boolean.class);
        return hasInvitation;
    }

    public GroupSearchResponse searchGroups(GroupSearchRequest req) {
        List<Map<String, Object>> results = this.jt.queryForList("SELECT groupId, groupName FROM groups WHERE groupName LIKE ? AND groupTypeId IN (1, 2) ORDER BY groupName LIMIT 12", req.search + "%");
        Map<Long, String> response = results.stream().collect(Collectors.toMap(r -> (long) r.get("groupId"), r -> (String) r.get("groupName")));
        return new GroupSearchResponse(200, true, "Successfully searched groupNames", response);
    }
}
