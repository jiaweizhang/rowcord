package rowcord.controllers;

/**
 * Created by jiawe on 1/17/2016.
 */

import databases.JDBC;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import requestdata.ProfileData;
import requestdata.RoleData;
import responses.StandardResponse;
import responses.subresponses.DataGen;
import utilities.TokenCreator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(
            value = "/role",
            method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse updateR(@RequestBody final RoleData pd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        List<String> roles = updateRoles(email, pd.getAccType());
        if (roles.size() == 0) {
            return new StandardResponse("error", "Failed during update roles - invalid role");
        }
        String jwt = TokenCreator.generateToken(email, roles);
        return new StandardResponse("success", "Successful role update", DataGen.createData(jwt));
    }

    private boolean updateProfile(String email, String firstName, String middleName, String lastName, String dob) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("UPDATE accounts " +
                    "SET firstname = ?, middlename = ?, lastname = ?, dob = ? " +
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
            st.setString(5, email);
            st.executeUpdate();
            st.close();
            return true;
        } catch (Exception f) {
            System.out.println("Failed during execution updateProfile");
            f.printStackTrace();
            return false;
        }
    }

    private List<String> updateRoles(String email, String accType) {
        List<String> roleList = new ArrayList<String>();
        if (accType.equals("member")) {
            roleList.add("workout");
            roleList.add("group");
        } else if (accType.equals("captain")) {
            roleList.add("workout");
            roleList.add("group");
            roleList.add("groupadmin");
        } else if (accType.equals("coach")) {
            roleList.add("group");
            roleList.add("coach");
        }
        if (!updateRolesDB(email, roleList)) {
            return new ArrayList<String>();
        }

        return roleList;
    }

    private boolean updateRolesDB(String email, List<String> roleList) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("DELETE FROM auth WHERE email = ?;");
            st.setString(1, email);
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            System.out.println("Failed prepared statement updateRoleDB");
            return false;
        }
        try {
            for (int i=0; i<roleList.size(); i++) {
                st = c.prepareStatement("INSERT INTO auth (email, role) VALUES (?, ?)");
                st.setString(1, email);
                st.setString(2, roleList.get(i));
                st.executeUpdate();
                st.close();
            }
            return true;
        } catch (Exception f) {
            System.out.println("Failed during execution updateRoleDB");
            return false;
        }
    }
}

