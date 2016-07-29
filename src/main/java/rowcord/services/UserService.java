package rowcord.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rowcord.models.requests.LoginRequest;
import rowcord.models.requests.RegistrationRequest;
import rowcord.models.responses.LoginResponse;
import rowcord.models.responses.RegistrationResponse;
import rowcord.models.responses.StdResponse;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Arrays;
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

    public RegistrationResponse register(RegistrationRequest registrationRequest) {

        List<SqlParameter> paramList = Arrays.asList(
                new SqlParameter("p_email", Types.VARCHAR),
                new SqlParameter("p_passhash", Types.VARCHAR),
                new SqlOutParameter("p_userId", Types.BIGINT),
                new SqlOutParameter("p_success", Types.BOOLEAN),
                new SqlOutParameter("p_message", Types.VARCHAR)
        );

        final String procedureCall = "{call sp_register(?, ?, ?, ?, ?)}";
        Map<String, Object> resultMap = this.jt.call(connection -> {

            CallableStatement callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1, registrationRequest.email);
            callableStatement.setString(2, passwordEncoder.encode(registrationRequest.password));
            callableStatement.registerOutParameter(3, Types.BIGINT);
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            return callableStatement;

        }, paramList);

        return new RegistrationResponse(
                200,
                (boolean) resultMap.get("p_success"),
                (String) resultMap.get("p_message"),
                (long) resultMap.get("p_userId")
        );
    }

    public StdResponse login(LoginRequest loginRequest, String ip) {
        List<Map<String, Object>> loginModels = this.jt.queryForList(
                "SELECT passhash, userId FROM users WHERE email = ?",
                new Object[]{loginRequest.email});

        if (loginModels.size() == 0) {
            return new StdResponse(200, false, "Invalid email");
        }
        Map<String, Object> loginModel = loginModels.get(0);

        if (passwordEncoder.matches(loginRequest.password, (String) loginModel.get("passhash"))) {
            String compactJws = Jwts.builder()
                    .setSubject(Long.toString((long) loginModel.get("userId")))
                    .signWith(SignatureAlgorithm.HS512, "secret key")
                    .compact();
            logLogin((long) loginModel.get("userId"), true, ip);
            return new LoginResponse(200, true, "Successfully logged in", compactJws);
        }
        logLogin((long) loginModel.get("userId"), false, ip);
        return new StdResponse(200, false, "Invalid password");
    }

    private void logLogin(long userId, boolean isSuccess, String ip) {
        this.jt.update("INSERT INTO loginLogs (userId, isSuccess, ip) VALUES (?, ?, ?::INET)",
                userId, isSuccess, ip);
    }
}
