package rowcord.processes;

import databases.JDBC;
import org.apache.tomcat.util.codec.binary.Base64;
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
        this.password = stringToByte(password);
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
            st.setString(1, email);
            byte[] salt = generateSalt();
            st.setString(2, generateHash(password, salt));
            st.setString(3, byteToString(salt));
            System.out.println("salt: "+Base64.encodeBase64String(salt));
            System.out.println("hash: "+generateHash(password, salt));
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
                String salt = rs.getString("salt");
                byte[] byteSalt = stringToByte(salt);
                String passhash = rs.getString("passhash");
                String token = rs.getString("token");
                st.close();
                rs.close();
                String calchash = generateHash(password, byteSalt);
                if (passhash.equals(calchash)) {
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

    private String generateHash(byte[] str, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( str );
            outputStream.write( salt );
            byte c[] = outputStream.toByteArray( );
            md.update(c);
            byte[] digest = md.digest();
            String output = byteToString(digest);
            return output;
        } catch(Exception e) {
            System.out.println("Failed during generatehash");
            return null;
        }
    }

    private String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    private byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
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
