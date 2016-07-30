package rowcord.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rowcord.models.requests.LoginRequest;
import rowcord.models.requests.RegistrationRequest;
import rowcord.models.responses.LoginResponse;
import rowcord.models.responses.RegistrationResponse;
import rowcord.models.responses.StdResponse;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

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
            return new StdResponse(200, true, "Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(registrationRequest.password);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jt.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO users (email, passhash) VALUES (?, ?)",
                            new String[]{"userid"});
                    ps.setString(1, registrationRequest.email);
                    ps.setString(2, hashedPassword);
                    return ps;
                },
                keyHolder);

        return new RegistrationResponse(200, false, "Successfully registered", keyHolder.getKey().longValue());
    }

    public StdResponse login(LoginRequest loginRequest, String ip) {
        List<Map<String, Object>> loginModels = this.jt.queryForList(
                "SELECT passhash, userId FROM users WHERE email = ?",
                new Object[]{loginRequest.email});

        if (loginModels.size() == 0) {
            return new StdResponse(200, true, "Invalid email");
        }
        Map<String, Object> loginModel = loginModels.get(0);

        if (passwordEncoder.matches(loginRequest.password, (String) loginModel.get("passhash"))) {
            String compactJws = Jwts.builder()
                    .setSubject(Long.toString((long) loginModel.get("userId")))
                    .signWith(SignatureAlgorithm.HS512, "secret key")
                    .compact();
            logLogin((long) loginModel.get("userId"), true, ip);
            return new LoginResponse(200, false, "Successfully logged in", compactJws);
        }
        logLogin((long) loginModel.get("userId"), false, ip);
        return new StdResponse(200, true, "Invalid password");
    }

    private void logLogin(long userId, boolean isSuccess, String ip) {
        this.jt.update("INSERT INTO loginLogs (userId, isSuccess, ip) VALUES (?, ?, ?::INET)",
                userId, isSuccess, ip);
    }
}
