package rowcord.models;

import databases.JDBC;
import org.apache.tomcat.util.codec.binary.Base64;
import responses.JsonResponse;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by jiawe on 1/17/2016.
 */
public class AccountModel {
    private String email;
    private byte[] password;

    public AccountModel(String email, String password) {
        this.email = email;
        this.password = stringToByte(password);
    }

    public JsonResponse addToDatabase() {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("INSERT INTO accounts (email, passhash, salt) VALUES (?, ?, ?);");
            //st = c.prepareStatement("INSERT INTO accounts (email, passhash, salt) VALUES (emale, pahs, salty);");
        } catch (Exception e) {
            System.out.println("Failed prepared statement");
            return getBadResponse();
        }
        try {
            st.setString(1, email);
            byte[] salt = generateSalt();
            st.setString(2, generateHash(password, salt));
            st.setString(3, Base64.encodeBase64String(salt));
            System.out.println("salt: "+Base64.encodeBase64String(salt));
            System.out.println("hash: "+generateHash(password, salt));
            st.executeUpdate();
            st.close();
        } catch (Exception f) {
            f.printStackTrace();
            System.out.println("Failed during execution");
            return getBadResponse();
        }
        return getGoodResponse();
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

    public String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    private JsonResponse getGoodResponse() {
        JsonResponse response = new JsonResponse("Ok", "Successfully added");
        return response;
    }

    private JsonResponse getBadResponse() {
        JsonResponse response = new JsonResponse("Bad", "Failed to add");
        return response;
    }
}
