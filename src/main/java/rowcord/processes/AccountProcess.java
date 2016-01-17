package rowcord.processes;

import databases.JDBC;

import responses.LoginResponse;
import responses.RegisterResponse;
import utilities.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            return getBadRegisterResponse();
        }
        try {
            System.out.println();
            System.out.println("register");
            st.setString(1, email);
            st.setString(2, PasswordHash.createHash(password));
            st.setString(3, "null");
            st.executeUpdate();
            st.close();
            return getGoodRegisterResponse();
        } catch (Exception f) {
            f.printStackTrace();
            System.out.println("Failed during execution");
            return getBadRegisterResponse();
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
                st.close();
                rs.close();
                if (PasswordHash.validatePassword(password, passhash)){
                    return getGoodLoginResponse(token);
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

    private LoginResponse getGoodLoginResponse(String token) {
        LoginResponse response = new LoginResponse("Ok", "Valid email and password", token);
        return response;
    }

    private LoginResponse getBadLoginResponse() {
        LoginResponse response = new LoginResponse("Bad", "Invalid email and password", "");
        return response;
    }
}
