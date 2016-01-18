package rowcord.controllers;

/**
 * Created by jiawe on 1/18/2016.
 */
import databases.JDBC;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import requestdata.group.ChangeRoleData;
import requestdata.group.CreateGroupData;
import responses.StandardResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse createGroup(@RequestBody final CreateGroupData gd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String groupName = gd.getGroupName();
        return createGroupDB(email, groupName);
    }

    @RequestMapping(
            value = "/changerole",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse changeRole(@RequestBody final ChangeRoleData rd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String groupName = rd.getGroupName();
        int admin = rd.getAdmin();
        int coach = rd.getCoach();
        return changeRoleDB( email,  groupName,  admin,  coach);
    }

    private StandardResponse createGroupDB(String email, String groupName) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT 1 FROM groups WHERE groupname = ?;");
            st.setString(1, groupName);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                st.close();
                rs.close();
                return new StandardResponse("error", "group already exists");
            }
            st.close();
            rs.close();

            st = c.prepareStatement("INSERT INTO groups (email, groupname, admin, coach, joindate) "+
            "VALUES (?, ?, 1, 0, ?);");
            st.setString(1, email);
            st.setString(2, groupName);
            st.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully created group");

        } catch (Exception f) {
            return new StandardResponse("error", "Failed to create group");
        }
    }

    private StandardResponse changeRoleDB(String email, String groupName, int admin, int coach) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT 1 FROM groups WHERE groupname = ? AND email = ? AND admin = 1;");
            st.setString(1, groupName);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                st.close();
                rs.close();
                return new StandardResponse("error", "you are not an admin in this group");
            }
            st.close();
            rs.close();

            if (admin == 0) {
                st = c.prepareStatement("SELECT 1 FROM groups WHERE groupname = ? AND email <> ? AND admin = 1;");
                st.setString(1, groupName);
                st.setString(2, email);

                rs = st.executeQuery();
                if (!rs.next()) {
                    st.close();
                    rs.close();
                    return new StandardResponse("error", "cannot change permission - no other admins");
                }
                st.close();
                rs.close();
            }

            st = c.prepareStatement("UPDATE groups SET admin = ?, coach = ? WHERE groupname = ?;");
            st.setInt(1, admin);
            st.setInt(2, coach);
            st.setString(3, groupName);
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully changed permissions");

        } catch (Exception f) {
            f.printStackTrace();
            return new StandardResponse("error", "Failed to change role");
        }
    }
}