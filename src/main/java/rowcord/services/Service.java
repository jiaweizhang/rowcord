package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiaweizhang on 1/30/16.
 */

@Transactional
@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private JdbcTemplate jt;

    protected boolean userExists(int userId) {
        int usersWithId = jt.queryForObject(
                "SELECT COUNT(*) FROM users WHERE user_id = ?;", Integer.class, userId);
        return usersWithId == 1;
    }

}
