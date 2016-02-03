package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import requestdata.group.*;
import responses.StandardResponse;
import responses.data.group.*;
import rowcord.data.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jiaweizhang on 1/22/16.
 */

@Transactional
@Service
public class GroupService extends rowcord.services.Service {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private NamedParameterJdbcTemplate njt;


    public StandardResponse createGroup(final CreateGroupRequest req, int userId) {

        int countOfGroupsWithName = jt.queryForObject(
                "SELECT COUNT(*) FROM groups WHERE group_name = ?;", Integer.class, req.getGroupName());

        if (countOfGroupsWithName != 0) {
            return new StandardResponse(true, 1002, "Group name exists already");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO groups (group_name, description, public_bool) VALUES (?, ?, ?);",
                                new String[]{"group_id"});
                        ps.setString(1, req.getGroupName());
                        ps.setString(2, req.getDescription());
                        ps.setBoolean(3, req.getPublicBool());
                        return ps;
                    }
                },
                keyHolder);

        int groupId = keyHolder.getKey().intValue();

        // TODO make sure user exists

        jt.update("INSERT INTO groupmembers (group_id, user_id, admin_bool, coach_bool) VALUES (?, ?, ?, ?);",
                groupId,
                userId,
                true,
                false);

        return new StandardResponse(false, 0, "Successfully created group", new CreateGroupData(groupId, req.getGroupName(), req.getDescription(), req.getPublicBool()));
    }

    public StandardResponse getMemberships(int userId) {
        List<MembershipGroup> groups = jt.query(
                "SELECT groups.group_id, groups.group_name, groupmembers.admin_bool, groupmembers.coach_bool, groupmembers.joindate " +
                        "FROM groups " +
                        "INNER JOIN groupmembers " +
                        "ON groups.group_id=groupmembers.group_id " +
                        "WHERE groupmembers.user_id = ? " +
                        "ORDER BY groups.group_name ASC;",
                new Object[]{userId},
                new RowMapper<MembershipGroup>() {
                    public MembershipGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        MembershipGroup group = new MembershipGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setAdminBool(rs.getBoolean("admin_bool"));
                        group.setCoachBool(rs.getBoolean("coach_bool"));
                        group.setJoinDate(rs.getTimestamp("joindate"));
                        return group;
                    }
                });
        return new StandardResponse(false, 0, "Successfully retrieved membership groups", new MembershipGroupData(groups));
    }

    public StandardResponse getPublics() {
        List<PartGroup> groups = jt.query(
                "SELECT group_id, group_name, description, createdate FROM groups WHERE public_bool = true;",
                new RowMapper<PartGroup>() {
                    public PartGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PartGroup group = new PartGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setDescription(rs.getString("description"));
                        group.setCreateDate(rs.getTimestamp("createdate"));
                        return group;
                    }
                });
        return new StandardResponse(false, 0, "Successfully retrieved public groups", new PublicGroupData(groups));
    }

    public StandardResponse getPrivates() {
        List<PartGroup> groups = jt.query(
                "SELECT group_id, group_name, description, createdate FROM groups WHERE public_bool = false;",
                new RowMapper<PartGroup>() {
                    public PartGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PartGroup group = new PartGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setDescription(rs.getString("description"));
                        group.setCreateDate(rs.getTimestamp("createdate"));
                        return group;
                    }
                });
        return new StandardResponse(false, 0, "Successfully retrieved private groups", new PublicGroupData(groups));
    }

    public StandardResponse getAll() {
        List<PartGroup> groups = jt.query(
                "SELECT group_id, group_name, description, createdate FROM groups;",
                new RowMapper<PartGroup>() {
                    public PartGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PartGroup group = new PartGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setDescription(rs.getString("description"));
                        group.setCreateDate(rs.getTimestamp("createdate"));
                        return group;
                    }
                });
        return new StandardResponse(false, 0, "Successfully retrieved all groups", new PublicGroupData(groups));
    }

    public StandardResponse getGroupById(int userId, int groupId) {
        // can see if public or if a member of private group

        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =?;", Integer.class, groupId, userId);

        boolean isPublic = jt.queryForObject(
                "SELECT public_bool FROM groups WHERE group_id = ?;", Boolean.class, groupId);

        // can't see if private and not in group
        if (inGroup != 1 && !isPublic) {
            return new StandardResponse(true, 3003, "You don't have access to this group");
        }


        ByIdGroup group = jt.queryForObject(
                "SELECT group_id, group_name, description, public_bool, createdate FROM groups WHERE group_id = ?;",
                new Object[]{groupId},
                new RowMapper<ByIdGroup>() {
                    public ByIdGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ByIdGroup group = new ByIdGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setDescription(rs.getString("description"));
                        group.setPublicBool(rs.getBoolean("public_bool"));
                        group.setCreateDate(rs.getTimestamp("createdate"));
                        return group;
                    }
                });

        List<Member> members = jt.query(
                "SELECT groupmembers.user_id, groupmembers.admin_bool, groupmembers.coach_bool, groupmembers.joindate, users.first_name, users.last_name " +
                        "FROM users " +
                        "INNER JOIN groupmembers " +
                        "ON users.user_id=groupmembers.user_id " +
                        "WHERE groupmembers.group_id = ? " +
                        "ORDER BY groupmembers.joindate ASC;",
                new Object[]{groupId},
                new RowMapper<Member>() {
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member();
                        member.setUserId(rs.getInt("user_id"));
                        member.setFirstName(rs.getString("first_name"));
                        member.setLastName(rs.getString("last_name"));
                        member.setAdminBool(rs.getBoolean("admin_bool"));
                        member.setCoachBool(rs.getBoolean("coach_bool"));
                        member.setJoinDate(rs.getTimestamp("joindate"));
                        return member;
                    }
                });

        return new StandardResponse(false, 0, "Successfully retrieved group", new ByIdGroupData(group, members));
    }

    public StandardResponse getGroupByName(int userId, String groupName) {
        groupName = groupName.replaceAll("\\+", " ");
        List<Integer> groupIds = jt.queryForList(
                "SELECT group_id FROM groups WHERE group_name = ?;",
                new Object[]{groupName},
                Integer.class);

        if (groupIds.size()!=1) {
            return new StandardResponse(true, 1008, "Groupname does not exist");
        }

        return getGroupById(userId, groupIds.get(0));
    }

    public StandardResponse apply(GroupApplicationRequest req, int userId) {

        // make sure group exists and user exists

        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =?;", Integer.class, req.getGroupId(), userId);

        if (inGroup == 1) {
            return new StandardResponse(true, 1003, "already in group");
        }

        int inApp = jt.queryForObject(
                "SELECT COUNT(*) FROM groupapplications WHERE group_id = ? AND user_id =?;", Integer.class, req.getGroupId(), userId);

        if (inApp == 1) {
            return new StandardResponse(true, 1004, "already applied");
        }

        jt.update("INSERT INTO groupapplications (group_id, user_id) VALUES (?, ?);",
                req.getGroupId(),
                userId);
        return new StandardResponse(false, 0, "successfully applied");

    }

    public StandardResponse getApplications(int userId, int groupId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, groupId, userId);

        if (inGroup != 1) {
            return new StandardResponse(true, 3004, "You can't view applications because you're either not an admin or not in the group");
        }

        List<Application> apps = jt.query(
                "SELECT groupapplications.user_id, groupapplications.applydate, users.first_name, users.last_name, groupapplications.group_id " +
                        "FROM users " +
                        "INNER JOIN groupapplications " +
                        "ON users.user_id=groupapplications.user_id " +
                        "WHERE groupapplications.group_id = ? " +
                        "ORDER BY groupapplications.applydate DESC;",
                new Object[]{groupId},
                new RowMapper<Application>() {
                    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Application app = new Application();
                        app.setUserId(rs.getInt("user_id"));
                        app.setFirstName(rs.getString("first_name"));
                        app.setLastName(rs.getString("last_name"));
                        app.setGroupId(rs.getInt("group_id"));
                        app.setApplyDate(rs.getTimestamp("applydate"));
                        return app;
                    }
                });
        return new StandardResponse(false, 0, "successfully retrieved applications", new ApplicationData(apps));
    }

    public StandardResponse accept(AcceptRequest req, int userId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, req.getGroupId(), userId);

        if (inGroup != 1) {
            return new StandardResponse(true, 3005, "You can't accept because you're either not an admin or not in the group");
        }

        int appExists = jt.queryForObject(
                "SELECT COUNT(*) FROM groupapplications WHERE group_id = ? AND user_id =?;", Integer.class, req.getGroupId(), req.getUserId());

        if (appExists != 1) {
            return new StandardResponse(true, 1502, "Application does not exist");
        }

        jt.update("DELETE FROM groupapplications WHERE group_id = ? AND user_id = ?;",
                req.getGroupId(),
                req.getUserId());

        jt.update("INSERT INTO groupmembers (group_id, user_id, admin_bool, coach_bool) VALUES (?, ?, ?, ?);",
                req.getGroupId(),
                req.getUserId(),
                false,
                false);

        return new StandardResponse(false, 0, "successfully accepted");
    }

    public StandardResponse kick(KickRequest req, int userId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, req.getGroupId(), userId);

        if (inGroup != 1) {
            return new StandardResponse(true, 3007, "You can't kick because you're either not an admin or not in the group");
        }

        int memberExists = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =?;", Integer.class, req.getGroupId(), req.getUserId());

        if (memberExists != 1) {
            return new StandardResponse(true, 1504, "Member does not exist");
        }

        jt.update("DELETE FROM groupmembers WHERE group_id = ? AND user_id = ?;",
                req.getGroupId(),
                req.getUserId());

        return new StandardResponse(false, 0, "successfully kicked member");
    }

    public StandardResponse changeRole(RoleRequest req, int userId) {
        int inGroup = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =? AND admin_bool = true;", Integer.class, req.getGroupId(), userId);
        if (inGroup != 1) {
            return new StandardResponse(true, 3006, "You can't change roles because you're either not an admin or not in the group");
        }

        int memberExists = jt.queryForObject(
                "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND user_id =?;", Integer.class, req.getGroupId(), req.getUserId());

        if (memberExists != 1) {
            return new StandardResponse(true, 1503, "Member is not in group");
        }

        if (userId == req.getUserId()) {
            // trying to change own role from admin
            if (!req.getAdminBool()) {
                int admins = jt.queryForObject(
                        "SELECT COUNT(*) FROM groupmembers WHERE group_id = ? AND admin_bool = true;", Integer.class, req.getGroupId());
                if (admins <= 1) {
                    return new StandardResponse(true, 1006, "You can't change role to non-admin because group has to have admin");
                }
            }

        }

        jt.update("UPDATE groupmembers SET admin_bool = ?, coach_bool = ? WHERE user_id = ? AND group_id = ?;",
                req.getAdminBool(),
                req.getCoachBool(),
                req.getUserId(),
                req.getGroupId());

        return new StandardResponse(false, 0, "successfully changed role");
    }
}
