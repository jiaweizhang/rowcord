package rowcord.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import responses.StandardResponse;
import responses.data.auth.Token;
import rowcord.data.User;
import utilities.PasswordHash;
import utilities.TokenCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jiaweizhang on 1/22/16.
 */
@Transactional
@Service
public class AuthenticationService extends rowcord.services.Service {

    @Autowired
    private JdbcTemplate jt;

    public StandardResponse register(String email, char[] password, String firstName, String lastName) {
        String passwordHash = null;
        try {
            passwordHash = PasswordHash.createHash(password);
        } catch (Exception f) {
            return new StandardResponse(true, 3001, "Failed during password hashing.");
        }
        int exists = jt.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?;", Integer.class, email);
        if (exists != 0) {
            return new StandardResponse(true, 1001, "Email already exists");
        }
        int returnedValue = jt.update(
                "INSERT INTO users (email, passhash, first_name, last_name, verified) VALUES (?, ?, ?, ?, ?);",
                email, passwordHash, firstName, lastName, false);
        return new StandardResponse(false, 0, "Successfully registered.");
    }

    public StandardResponse login(String email, char[] password) {
        List<User> users = jt.query(
                "SELECT user_id, passhash FROM users WHERE email = ?;",
                new Object[]{email},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setUserId(rs.getInt("user_id"));
                        user.setPasshash(rs.getString("passhash"));
                        return user;
                    }
                });
        if (users.size() != 1) {
            return new StandardResponse(true, 1501, "Email does not exist");
        }
        try {
            if (PasswordHash.validatePassword(password, users.get(0).getPasshash())) {
                Token token = new Token(TokenCreator.generateToken(users.get(0).getUserId()));
                return new StandardResponse(false, 0, "Successfully authenticated.", token);
            }
        } catch (Exception e) {
            return new StandardResponse(true, 3002, "Failed to validate password.");
        }
        return new StandardResponse(true, 3002, "Failed to validate password.");
    }
}
