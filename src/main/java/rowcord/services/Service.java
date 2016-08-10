package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import rowcord.models.GroupPermissions;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@org.springframework.stereotype.Service
class Service {

    @Autowired
    JdbcTemplate jt;

    public boolean isValidUser(long userId) {
        return this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM users WHERE userId = ?)", new Object[]{userId}, Boolean.class);
    }

    public boolean isValidGroup(long groupId) {
        return this.jt.queryForObject("SELECT EXISTS(SELECT 1 FROM groups WHERE groupId = ?)", new Object[]{groupId}, Boolean.class);
    }

    public GroupPermissions getGroupPermissionsForUser(long userId, long groupId) {
        List<SqlParameter> paramList = Arrays.asList(
                new SqlParameter("p_groupId", Types.BIGINT),
                new SqlParameter("p_userId", Types.BIGINT),
                new SqlOutParameter("p_permissions", Types.BIGINT),
                new SqlOutParameter("p_success", Types.BOOLEAN),
                new SqlOutParameter("p_message", Types.VARCHAR)
        );

        final String procedureCall = "{call sp_getGroupPermissions(?, ?, ?, ?, ?)}";
        Map<String, Object> resultMap = this.jt.call(connection -> {

            CallableStatement callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setLong(1, userId);
            callableStatement.setLong(2, groupId);
            callableStatement.registerOutParameter(3, Types.BIGINT);
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            return callableStatement;

        }, paramList);

        return new GroupPermissions((long) resultMap.get("p_permissions"), (boolean) resultMap.get("p_success"), (String) resultMap.get("p_message"));
    }
}
