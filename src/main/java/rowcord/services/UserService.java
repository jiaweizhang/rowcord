package rowcord.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rowcord.models.requests.LoginRequest;
import rowcord.models.requests.RegistrationRequest;
import rowcord.models.responses.LoginResponse;
import rowcord.models.responses.RegistrationResponse;
import rowcord.models.responses.StdResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@Transactional
@Service
public class UserService extends rowcord.services.Service {

    private BCryptPasswordEncoder passwordEncoder;

    public UserService() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public StdResponse register(RegistrationRequest registrationRequest) {
        int emailCount = this.jt.queryForObject(
                "SELECT COUNT(*) FROM users WHERE email = ?",
                new Object[]{registrationRequest.email}, Integer.class);
        if (emailCount != 0) {
            return new StdResponse("Bad", "Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(registrationRequest.password);
        // persist in database

        this.jt.update(
                "INSERT INTO users (email, passhash) VALUES (?, ?)",
                registrationRequest.email, hashedPassword);
        return new RegistrationResponse("Ok", "Successfully registered", "jwt here");
    }

    public StdResponse login(LoginRequest loginRequest) {
        LoginModel loginModel = this.jt.queryForObject(
                "SELECT passhash, userId FROM users WHERE email = ?",
                new Object[]{loginRequest.email},
                new RowMapper<LoginModel>() {
                    public LoginModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        LoginModel loginModel = new LoginModel();
                        loginModel.passhash = rs.getString("passhash");
                        loginModel.userId = rs.getLong("userId");
                        return loginModel;
                    }
                });

        if (passwordEncoder.matches(loginRequest.password, loginModel.passhash)) {
            String compactJws = Jwts.builder()
                    .setSubject(Long.toString(loginModel.userId))
                    .signWith(SignatureAlgorithm.HS512, "secret key")
                    .compact();
            return new LoginResponse("Ok", "Successfully logged in", compactJws);
        }

        return new StdResponse("Bad", "Invalid password");
    }

    private class LoginModel {
        private String passhash;
        private long userId;
    }
}
