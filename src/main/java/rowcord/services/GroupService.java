package rowcord.services;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.requests.GroupSearchRequest;
import rowcord.models.requests.InviteUserRequest;
import rowcord.models.responses.GroupCreationResponse;
import rowcord.models.responses.GroupSearchResponse;
import rowcord.models.responses.StdResponse;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
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
        int groupNameCount = this.jt.queryForObject(
                "SELECT COUNT(*) FROM groups WHERE groupName = ?",
                new Object[]{groupCreationRequest.groupName}, Integer.class);
        if (groupNameCount != 0) {
            return new StdResponse(200, "Group name already exists");
        }

        if (groupCreationRequest.groupTypeId < 1 || groupCreationRequest.groupTypeId > 3) {
            return new StdResponse(200, "Group type not valid");
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

        return new GroupCreationResponse(200, "Successfully created group", keyHolder.getKey().longValue());
    }

    public StdResponse inviteUsers(InviteUserRequest req) {
        // validate list
        if (req.userIds.size() == 0) {
            return new StdResponse(200, "Cannot add empty member list");
        }

        if (!req.userIds.stream().allMatch(new HashSet<>()::add)) {
            // duplicates exist
            return new StdResponse(200, "Cannot add duplicate userIds");
        }

        if (!groupExists(req.groupId)) {
            return new StdResponse(200, "Group does not exist");
        }

        for (long m : req.userIds) {
            if (!userExists(m)) {
                return new StdResponse(200, "User " + m + " does not exist");
            }
            if (memberIsInGroup(m, req.groupId)) {
                return new StdResponse(200, "User " + m + " is already in the group");
            }
            if (memberHasInvitation(m, req.groupId)) {
                return new StdResponse(200, "User " + m + " is already has an invitation");
            }
        }

        List<Object[]> batch = new ArrayList<Object[]>();
        for (long m : req.userIds) {
            batch.add(new Object[]{m, req.groupId});
        }
        int[] updateCounts = this.jt.batchUpdate("INSERT INTO groupInvitations (userId, groupId) VALUES (?, ?)", batch);
        long totalUpdateCounts = IntStream.of(updateCounts).sum();
        return new StdResponse(200, totalUpdateCounts + " userIds added to group");
    }

    private boolean memberIsInGroup(long userId, long groupId) {
        boolean isInGroup = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groupMembers WHERE userId = ? AND groupId = ?)", new Object[]{userId, groupId}, Boolean.class);
        return isInGroup;
    }

    private boolean memberHasInvitation(long userId, long groupId) {
        boolean hasInvitation = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groupInvitations WHERE userId = ? AND groupId = ?)", new Object[]{userId, groupId}, Boolean.class);
        return hasInvitation;
    }

    private boolean userExists(long userId) {
        boolean userExists = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM users WHERE userId = ?)", new Object[]{userId}, Boolean.class);
        return userExists;
    }

    private boolean groupExists(long groupId) {
        boolean groupExists = this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groups WHERE groupId = ?)", new Object[]{groupId}, Boolean.class);
        return groupExists;
    }

    public GroupSearchResponse searchGroups(GroupSearchRequest req) {
        List<Map<String, Object>> results = this.jt.queryForList("SELECT groupId, groupName FROM groups WHERE groupName LIKE ? AND groupTypeId IN (1, 2) ORDER BY groupName LIMIT 12", req.search + "%");
        Map<Long, String> response = results.stream().collect(Collectors.toMap(r -> (long) r.get("groupId"), r -> (String) r.get("groupName")));
        return new GroupSearchResponse(200, "Successfully searched groupNames", response);
    }
}
