package rowcord.processes;

import databases.JDBC;

import responses.LoginResponse;
import responses.RegisterResponse;
import utilities.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import utilities.TokenCreator;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by jiawe on 1/17/2016.
 */
public class AccountProcess {
    private String email;
    private char[] password;

    public AccountProcess(String email, String password) {
        this.email = email;
        this.password = password.toCharArray();
    }

    public RegisterResponse register() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("INSERT INTO accounts (email, passhash) VALUES (?, ?);");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return getBadRegisterResponse("Failed prepared statement");
        }
        String passwordHash = null;
        try {
            passwordHash = PasswordHash.createHash(password);
        } catch (Exception f) {
            return getBadRegisterResponse("Failed during hashing");
        }
        try {
            st.setString(1, email);
            st.setString(2, passwordHash);
            st.executeUpdate();
            st.close();
        } catch (Exception g) {
            return getBadRegisterResponse(g.getMessage());
        }
        try {
            st = c.prepareStatement("INSERT INTO auth (email, role) VALUES (?, 'new');");
        } catch (Exception h) {
            System.out.println("Failed prepared statement");
            return getBadRegisterResponse("Failed prepared statement");
        }
        try {
            st.setString(1, email);
            st.executeUpdate();
            st.close();
            return getGoodRegisterResponse();
        } catch (Exception g) {
            return getBadRegisterResponse(g.getMessage());
        }
    }

    public LoginResponse login() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT passhash FROM accounts WHERE email = ?;");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return getBadLoginResponse();
        }
        try {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String passhash = rs.getString("passhash");
                st.close();
                rs.close();
                if (PasswordHash.validatePassword(password, passhash)) {
                    List<String> roleList = getRoles(c);
                    String jwt = TokenCreator.generateToken(email, roleList);
                    return getGoodLoginResponse(jwt);
                } else {
                    return getBadLoginResponse();
                }
            }
            return getBadLoginResponse();
        } catch (Exception f) {
            f.printStackTrace();
            System.out.println("Failed during execution");
            return getBadLoginResponse();
        }
    }

    private List<String> getRoles(Connection c) {
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT role FROM auth WHERE email = ?;");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return null;
        }
        try {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            List<String> roleList = new ArrayList<String>();
            while (rs.next()) {
                String role = rs.getString("role");
                roleList.add(role);
            }
            st.close();
            rs.close();
            return roleList;
        } catch (Exception f) {
            f.printStackTrace();
            System.out.println("Failed during execution");
            return null;
        }
    }

    private RegisterResponse getGoodRegisterResponse() {
        RegisterResponse response = new RegisterResponse("Ok", "Successfully added");
        return response;
    }

    private RegisterResponse getBadRegisterResponse() {
        RegisterResponse response = new RegisterResponse("Bad", "Failed to add");
        return response;
    }

    private RegisterResponse getBadRegisterResponse(String message) {
        RegisterResponse response = new RegisterResponse("Bad", message);
        return response;
    }

    private LoginResponse getGoodLoginResponse(String token) {
        LoginResponse response = new LoginResponse("Ok", "Valid email and password", token);
        return response;
    }

    private LoginResponse getBadLoginResponse() {
        LoginResponse response = new LoginResponse("Bad", "Invalid email and password", "");
        return response;
    }

    private LoginResponse getBadLoginResponse(String message) {
        LoginResponse response = new LoginResponse("Bad", message, "");
        return response;
    }
}
