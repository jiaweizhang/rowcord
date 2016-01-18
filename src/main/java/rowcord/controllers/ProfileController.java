package rowcord.controllers;

/**
 * Created by jiawe on 1/17/2016.
 */

import databases.JDBC;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import requestdata.ProfileData;
import responses.StandardResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @RequestMapping(
            value = "/info",
            method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse update(@RequestBody final ProfileData pd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        if (!updateProfile(email, pd.getFirstName(), pd.getMiddleName(), pd.getLastName(), pd.getDob())) {
            return new StandardResponse("error", "Failed to update profile");
        }
        return new StandardResponse("success", "Successfully updated profile");
    }

    private boolean updateProfile(String email, String firstName, String middleName, String lastName, String dob) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("UPDATE accounts " +
                    "SET firstname = ?, middlename = ?, lastname = ?, dob = ? , verify = ? " +
                    "WHERE email = ?;");
        } catch (Exception e) {
            System.out.println("Failed prepared statement updateProfile");
            e.printStackTrace();
            return false;
        }
        try {
            st.setString(1, firstName);
            st.setString(2, middleName);
            st.setString(3, lastName);
            st.setDate(4, Date.valueOf(dob));
            st.setInt(5, 1);
            st.setString(6, email);
            st.executeUpdate();
            st.close();
            return true;
        } catch (Exception f) {
            System.out.println("Failed during execution updateProfile");
            f.printStackTrace();
            return false;
        }
    }
}

