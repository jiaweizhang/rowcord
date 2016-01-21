package rowcord.controllers;

/**
 * Created by jiaweizhang on 1/16/2016.
 */

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import requestdata.auth.UserRequest;
import responses.StandardResponse;
import utilities.PasswordHash;
import utilities.TokenCreator;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/auth/account")
public class AuthenticationController {

    @Autowired
    private JdbcTemplate jt;

    public void setDataSource(DataSource dataSource) {
        jt = new JdbcTemplate(dataSource);
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse register(@RequestBody final UserRequest req) {
        String passwordHash = null;
        try {
            passwordHash = PasswordHash.createHash(req.getPassword());
        } catch (Exception f) {
            return new StandardResponse(true, "Failed during password hashing.");
        }
        int exists = jt.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?;", Integer.class, req.getEmail());
        if (exists != 0) {
            return new StandardResponse(true, "Email already exists");
        }
        int returnedValue = jt.update(
                "INSERT INTO users (email, passhash) VALUES (?, ?);",
                req.getEmail(), passwordHash);
        return new StandardResponse(false, "Successfully registered.");
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse login(@RequestBody final UserRequest req) {
        List<User> user = jt.query(
                "SELECT user_id, passhash FROM users WHERE email = ?;",
                new Object[]{req.getEmail()},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User actor = new User(rs.getInt("user_id"), rs.getString("passhash"));
                        return actor;
                    }
                });
        if (user.size() != 1) {
            return new StandardResponse(true, "Email does not exist");
        }
        try {
            if (PasswordHash.validatePassword(req.getPassword(), user.get(0).passhash)) {
                Token token = new Token(TokenCreator.generateToken(user.get(0).user_id));
                return new StandardResponse(false, "Successfully authenticated.", null, token);
            }
        } catch (Exception e) {
            return new StandardResponse(true, "Failed to validate password.");
        }
        return new StandardResponse(true, "Failed to validate password.");
    }

    private final static class User {
        private int user_id;
        private String passhash;

        public User(int user_id, String passhash) {
            this.user_id = user_id;
            this.passhash = passhash;
        }
    }

    private final static class Token {
        private String token;

        public Token(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}


