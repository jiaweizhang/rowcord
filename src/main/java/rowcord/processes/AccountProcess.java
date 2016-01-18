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

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

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
            st = c.prepareStatement("INSERT INTO accounts (email, passhash, salt) VALUES (?, ?, ?);");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return getBadRegisterResponse("Failed prepared statement");
        }
        String passwordHash = null;
        try {
            passwordHash = PasswordHash.createHash(password);
        } catch (Exception g) {
            return getBadRegisterResponse("Failed during hashing");
        }
        try {
            st.setString(1, email);
            st.setString(2, passwordHash);
            st.setString(3, "null");
            st.executeUpdate();
            st.close();
            return getGoodRegisterResponse();
        } catch (Exception f) {
            return getBadRegisterResponse(f.getMessage());
        }
    }

    public LoginResponse login() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT passhash, salt, token FROM accounts WHERE email = ?;");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return getBadLoginResponse();
        }
        try {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println();
                System.out.println("login");

                String salt = rs.getString("salt");
                String passhash = rs.getString("passhash");
                String token = rs.getString("token");
                LocalDate now = LocalDate.now();
                Date nowDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
                LocalDate expire = LocalDate.now().plusDays(7);
                Date expireDate = Date.from(expire.atStartOfDay(ZoneId.systemDefault()).toInstant());
                String jwt = Jwts.builder().setSubject(email)
                        .claim("roles", Arrays.asList("user", "admin")).setIssuedAt(nowDate)
                        .setExpiration(expireDate)
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
                st.close();
                rs.close();
                if (PasswordHash.validatePassword(password, passhash)) {
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
