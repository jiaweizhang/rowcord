package rowcord.processes;

import databases.JDBC;

import responses.StandardResponse;
import responses.subresponses.DataGen;
import utilities.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utilities.TokenCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiawe on 1/17/2016.
 */
public class AuthenticationProcess {
    private String email;
    private char[] password;

    public AuthenticationProcess(String email, String password) {
        this.email = email;
        this.password = password.toCharArray();
    }

    public StandardResponse register() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("INSERT INTO accounts (email, passhash, profile, verify) VALUES (?, ?, 0, 0);");
        } catch (Exception e) {
            return new StandardResponse("error", "Failed prepared statement in register");
        }
        String passwordHash = null;
        try {
            passwordHash = PasswordHash.createHash(password);
        } catch (Exception f) {
            return new StandardResponse("error", "Failed during hashing in register");
        }
        try {
            st.setString(1, email);
            st.setString(2, passwordHash);
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully registered");
        } catch (Exception g) {
            return new StandardResponse("error", "Failed during execution in register");
        }
    }

    public StandardResponse login() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT passhash, profile, verify FROM accounts WHERE email = ?;");
        } catch (Exception e) {
            return getBadLoginResponse("Failed prepared statement during login");
        }
        try {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String passhash = rs.getString("passhash");
                int profile = rs.getInt("profile");
                int verify = rs.getInt("verify");
                st.close();
                rs.close();
                if (PasswordHash.validatePassword(password, passhash)) {
                    String jwt = TokenCreator.generateToken(email, profile, verify);
                    return getGoodLoginResponse(jwt);
                } else {
                    return getBadLoginResponse("Invalid username or password");
                }
            }
            return getBadLoginResponse("Invalid username or password");
        } catch (Exception f) {
            return getBadLoginResponse("Failed during execution");
        }
    }

    private StandardResponse getGoodLoginResponse(String token) {
        StandardResponse response = new StandardResponse("success", "Valid email and password", DataGen.createData(token));
        return response;
    }

    private StandardResponse getBadLoginResponse(String message) {
        StandardResponse response = new StandardResponse("error", message);
        return response;
    }
}
