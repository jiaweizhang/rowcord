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

    public StdResponse register(RegistrationRequest registrationRequest) {

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

        if ((boolean) resultMap.get("p_success")) {
            return new RegistrationResponse(
                    200,
                    true,
                    (String) resultMap.get("p_message"),
                    (long) resultMap.get("p_userId")
            );
        }
        return new StdResponse(200, false, (String) resultMap.get("p_message"));
    }

    public StdResponse login(LoginRequest loginRequest, String ip) {
        List<SqlParameter> paramList = Arrays.asList(
                new SqlParameter("p_email", Types.VARCHAR),
                new SqlOutParameter("p_passhash", Types.VARCHAR),
                new SqlOutParameter("p_userId", Types.BIGINT),
                new SqlOutParameter("p_success", Types.BOOLEAN),
                new SqlOutParameter("p_message", Types.VARCHAR)
        );

        final String procedureCall = "{call sp_login(?, ?, ?, ?, ?)}";
        Map<String, Object> resultMap = this.jt.call(connection -> {

            CallableStatement callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setString(1, loginRequest.email);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.BIGINT);
            callableStatement.registerOutParameter(4, Types.BOOLEAN);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            return callableStatement;

        }, paramList);

        boolean success = (boolean) resultMap.get("p_success");
        if (!success) {
            return new StdResponse(200, false, (String) resultMap.get("p_message"));
        }

        if (passwordEncoder.matches(loginRequest.password, (String) resultMap.get("p_passhash"))) {
            String compactJws = Jwts.builder()
                    .setSubject(Long.toString((long) resultMap.get("p_userId")))
                    .signWith(SignatureAlgorithm.HS512, "secret key")
                    .compact();
            return new LoginResponse(200, true, "Successfully logged in", compactJws);
        }
        return new StdResponse(200, false, "Invalid password");
    }

}
