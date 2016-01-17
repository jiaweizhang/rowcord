package rowcord.processes;

import databases.JDBC;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import responses.LoginResponse;
import responses.RegisterResponse;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by jiawe on 1/17/2016.
 */
public class AccountProcess {
    private String email;
    private byte[] password;

    public AccountProcess(String email, String password) {
        this.email = email;
        System.out.println("prepassword : "+password);
        this.password = stringToByte(password);
        System.out.println("postpassword: "+this.password);
        System.out.println("erggpassword: "+byteToString(this.password));
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
            byte[] salt = generateSalt();
            st.setString(2, byteToString(generateHash(salt)));
            st.setString(3, byteToString(salt));
            System.out.println("salt: "+byteToString(salt));
            System.out.println("password: "+password);
            System.out.println("bytehash: "+generateHash(salt));
            System.out.println("passhash: "+byteToString(generateHash(salt)));
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
                System.out.println("salt: "+salt);
                byte[] byteSalt = stringToByte(salt);
                String passhash = rs.getString("passhash");
                String token = rs.getString("token");
                st.close();
                rs.close();
                byte[] bytehash = stringToByte(passhash);
                byte[] calchash = generateHash(byteSalt);
                String finalhash = byteToString(calchash);
                System.out.println("password: "+password);
                System.out.println("passhash: "+passhash);
                System.out.println("bytehash: "+bytehash);
                System.out.println("calchash: "+calchash);
                if (finalhash.equals(passhash)) {
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

    private byte[] generateSalt() {
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch( Exception e) {
            System.out.println("Failed during generateSalt");
            return null;
        }
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] generateHash(byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( password );
            outputStream.write( salt );
            byte c[] = outputStream.toByteArray( );
            md.update(c);
            byte[] digest = md.digest();
            return digest;
        } catch(Exception e) {
            System.out.println("Failed during generatehash");
            return null;
        }
    }

    private String byteToString(byte[] input) {
        String encoded = Base64.getEncoder().encodeToString(input);
        return encoded;
    }

    private byte[] stringToByte(String input) {
        byte[] decoded = Base64.getDecoder().decode(input);
        return decoded;
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
