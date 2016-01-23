package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import requestdata.group.CreateGroupRequest;
import responses.StandardResponse;
import responses.data.group.CreateGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        return new StandardResponse(false, "Successfully created group", new CreateGroup(groupId, req.getGroupName(), req.getDescription(), req.getPublicBool()));
    }
}
