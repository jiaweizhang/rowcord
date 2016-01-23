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
import requestdata.group.CreateGroupRequest;
import responses.StandardResponse;
import responses.data.group.CreateGroupData;
import responses.data.group.MembershipGroupData;
import responses.data.group.PublicGroupData;
import rowcord.data.MembershipGroup;
import rowcord.data.PublicGroup;

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
public class GroupService {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private NamedParameterJdbcTemplate njt;


    public StandardResponse createGroup(final CreateGroupRequest req, int userId) {

        int countOfGroupsWithName = jt.queryForObject(
                "SELECT COUNT(*) FROM groups WHERE group_name = ?", Integer.class, req.getGroupName());

        if (countOfGroupsWithName != 0) {
            return new StandardResponse(true, "Group name exists already");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jt.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO groups (group_name, description, public_bool) VALUES (?, ?, ?);",
                                new String[] {"group_id"});
                        ps.setString(1, req.getGroupName());
                        ps.setString(2, req.getDescription());
                        ps.setBoolean(3, req.getPublicBool());
                        return ps;
                    }
                },
                keyHolder);

        int groupId = keyHolder.getKey().intValue();

        jt.update("INSERT INTO groupmembers (group_id, user_id, admin_bool, coach_bool) VALUES (?, ?, ?, ?);",
                groupId,
                userId,
                true,
                false);

        return new StandardResponse(false, "Successfully created group", new CreateGroupData(groupId, req.getGroupName(), req.getDescription(), req.getPublicBool()));
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
        return new StandardResponse(false, "Successfully retrieved membership groups", new MembershipGroupData(groups));
    }

    public StandardResponse getPublics() {
        List<PublicGroup> groups = jt.query(
                "SELECT group_id, group_name, description, createdate FROM groups WHERE public_bool = true",
                new RowMapper<PublicGroup>() {
                    public PublicGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
                        PublicGroup group = new PublicGroup();
                        group.setGroupId(rs.getInt("group_id"));
                        group.setGroupName(rs.getString("group_name"));
                        group.setDescription(rs.getString("description"));
                        group.setCreateDate(rs.getTimestamp("createdate"));
                        return group;
                    }
                });
        return new StandardResponse(false, "Successfully retrieved public groups", new PublicGroupData(groups));
    }
}
