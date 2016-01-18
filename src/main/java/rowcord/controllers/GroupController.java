package rowcord.controllers;

/**
 * Created by jiawe on 1/18/2016.
 */
import databases.JDBC;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import requestdata.group.*;
import responses.StandardResponse;
import responses.subresponses.ApplicationResponse;
import responses.subresponses.GroupDetailResponse;
import responses.subresponses.GroupResponse;
import responses.subresponses.MembershipResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @RequestMapping(
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getGroups(final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        return getAllGroupsDB(email);
    }


    @RequestMapping(
            value = "/memberships",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getMembership(final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        return getMembershipDB(email);
    }


    @RequestMapping(
            value = "/groupdetail",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse getGroupDetail(@RequestBody final GroupDetailData gd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String groupName = gd.getGroupName();
        groupName = groupName.replaceAll("\\+", " ");
        return getGroupDetailDB(email, groupName);
    }


    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse createGroup(@RequestBody final CreateGroupData gd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String groupName = gd.getGroupName();
        String groupDescription = gd.getGroupDescription();
        return createGroupDB(email, groupName, groupDescription);
    }

    @RequestMapping(
            value = "/changerole",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse changeRole(@RequestBody final ChangeRoleData rd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String otherEmail = rd.getEmail();
        if (otherEmail == null) {
            otherEmail = email;
        }
        String groupName = rd.getGroupName();
        int admin = rd.getAdmin();
        int coach = rd.getCoach();
        return changeRoleDB( email, otherEmail, groupName,  admin,  coach);
    }

    @RequestMapping(
            value = "/apply",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse apply(@RequestBody final ApplyData ad, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String groupName = ad.getGroupName();

        return applyDB(email, groupName);
    }

    @RequestMapping(
            value = "/applications",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse applications(final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();

        return applicationsDB(email);
    }

    @RequestMapping(
            value = "/applicationaccept",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse applicationAccept(@RequestBody final AcceptData rd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String acceptedEmail = rd.getEmail();
        String groupName = rd.getGroupName();
        return applicationAcceptDB(email, acceptedEmail, groupName);
    }

    @RequestMapping(
            value = "/invite",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse invite(@RequestBody final InviteData rd, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        String invitedEmail = rd.getEmail();
        String groupName = rd.getGroupName();
        /**
         * TODO
         */
        return new StandardResponse("error", "invite not implemented");
    }

    private StandardResponse createGroupDB(String email, String groupName, String groupDescription) {
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
            "VALUES (?, ?, 1, 0, ?); INSERT INTO groupdetails (groupname, description, createdate) VALUES (?, ?, ?);");
            st.setString(1, email);
            st.setString(2, groupName);
            st.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            st.setString(4, groupName);
            st.setString(5, groupDescription);
            st.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully created group", new MembershipResponse(groupName, 1, 0));

        } catch (Exception f) {
            f.printStackTrace();
            return new StandardResponse("error", "Failed to create group");
        }
    }

    private StandardResponse changeRoleDB(String email, String otherEmail, String groupName, int admin, int coach) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            if (!validateAdmin(email, groupName, c)) {
                return new StandardResponse("error", "Permission denied - not group admin");
            }
            // email is admin
            if (admin == 0) {
                st = c.prepareStatement("SELECT 1 FROM groups WHERE groupname = ? AND email <> ? AND admin = 1;");
                st.setString(1, groupName);
                st.setString(2, email);
                // number of other admins is 1

                ResultSet rs = st.executeQuery();
                if (!rs.next()) {
                    st.close();
                    rs.close();
                    return new StandardResponse("error", "cannot change permission - no other admins");
                }
                st.close();
                rs.close();
            }

            st = c.prepareStatement("UPDATE groups SET admin = ?, coach = ? WHERE groupname = ? AND email = ?;");
            st.setInt(1, admin);
            st.setInt(2, coach);
            st.setString(3, groupName);
            st.setString(4, otherEmail);
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully changed permissions");

        } catch (Exception f) {
            f.printStackTrace();
            return new StandardResponse("error", "Failed to change permissions");
        }
    }

    private StandardResponse getMembershipDB(String email) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT groupname, admin, coach FROM groups WHERE email = ?;");
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            List<MembershipResponse> md = new ArrayList<MembershipResponse>();
            while (rs.next()) {
                String groupName = rs.getString("groupname");
                int admin = rs.getInt("admin");
                int coach = rs.getInt("coach");
                MembershipResponse mr = new MembershipResponse(groupName, admin, coach);
                md.add(mr);
            }
            st.close();
            rs.close();

            if  (md.size() == 0) {
                return new StandardResponse("success", "not in any groups", md);
            }
            return new StandardResponse("success", "Successfully fetched groups", md);

        } catch (Exception f) {
            return new StandardResponse("error", "Failed to check group membership");
        }
    }

    private StandardResponse getAllGroupsDB(String email) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT groupname, description, createdate FROM groupdetails;");

            ResultSet rs = st.executeQuery();
            List<GroupResponse> md = new ArrayList<GroupResponse>();
            while (rs.next()) {
                String groupName = rs.getString("groupname");
                String groupDescription = rs.getString("description");
                Date createDate = rs.getDate("createdate");
                GroupResponse mr = new GroupResponse(groupName, groupDescription, createDate);
                md.add(mr);
            }
            st.close();
            rs.close();

            if  (md.size() == 0) {
                return new StandardResponse("success", "no groups", md);
            }
            return new StandardResponse("success", "Successfully fetched groups", md);

        } catch (Exception f) {
            return new StandardResponse("error", "Failed to lookup groups");
        }
    }

    private StandardResponse getGroupDetailDB(String email, String groupName) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT description, createdate FROM groupdetails WHERE groupname = ?;");
            st.setString(1, groupName);

            ResultSet rs = st.executeQuery();
            String groupDescription;
            Date createDate;

            if (rs.next()) {
                groupDescription = rs.getString("description");
                createDate = rs.getDate("createdate");
                st.close();
                rs.close();
            } else {
                st.close();
                rs.close();
                return new StandardResponse("error", "group not found");
            }

            st = c.prepareStatement("SELECT email, admin FROM groups WHERE groupname = ?;");
            st.setString(1, groupName);

            rs = st.executeQuery();
            List<String> admins = new ArrayList<String>();
            List<String> members = new ArrayList<String>();
            boolean isAdmin = false;

            while (rs.next()) {
                String currentEmail = rs.getString("email");
                int admin = rs.getInt("admin");
                members.add(currentEmail);
                if (admin == 1) {
                    admins.add(currentEmail);
                    if (currentEmail.equals(email)) {
                        isAdmin = true;
                    }
                } 
            }
            st.close();
            rs.close();
            return new StandardResponse("success", "Successfully fetched group detail",
                    new GroupDetailResponse(groupName, groupDescription, createDate, admins, isAdmin, members));

        } catch (Exception f) {
            f.printStackTrace();
            return new StandardResponse("error", "Failed to fetch group detail");
        }
    }

    private StandardResponse applyDB(String email, String groupName) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("INSERT INTO groupapplications (email, groupname, applydate) "+
                    "VALUES (?, ?, ?);");
            st.setString(1, email);
            st.setString(2, groupName);
            st.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully applied to group");
        } catch (Exception f) {
            return new StandardResponse("error", "Failed to apply to group - already applied");
        }
    }

    private StandardResponse applicationsDB(String email) {
        Connection c = JDBC.connect();
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT email, groupname, applydate FROM groupapplications WHERE groupname "+
                    "IN (SELECT groupname FROM groups WHERE email = ? AND admin = 1);");
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            List<ApplicationResponse> md = new ArrayList<ApplicationResponse>();
            while (rs.next()) {
                String applyEmail = rs.getString("email");
                String groupName = rs.getString("groupname");
                Date applyDate = rs.getDate("applydate");
                ApplicationResponse ar = new ApplicationResponse(applyEmail, groupName, applyDate);
                md.add(ar);
            }
            st.close();
            rs.close();

            if  (md.size() == 0) {
                return new StandardResponse("success", "no applications", md);
            }
            return new StandardResponse("success", "Successfully fetched applications", md);

        } catch (Exception f) {
            f.printStackTrace();
            return new StandardResponse("error", "Failed to fetch applications");
        }
    }

    private StandardResponse applicationAcceptDB(String email, String acceptedEmail, String groupName) {
        // validate admin
        Connection c = JDBC.connect();
        if (!validateAdmin(email, groupName, c)) {
            return new StandardResponse("error", "Permission denied - not group admin");
        }
        PreparedStatement st = null;
        // delete from applications
        try {
            st = c.prepareStatement("DELETE FROM groupapplications " +
                    "WHERE email = ? AND groupname = ?;");
            st.setString(1, acceptedEmail);
            st.setString(2, groupName);
            st.executeUpdate();
            st.close();
        } catch (Exception f) {

            return new StandardResponse("error", "Failed to delete application");
        }
        // add to group
        try {
            st = c.prepareStatement("INSERT INTO groups (email, groupname, admin, coach, joindate) "+
                    "VALUES (?, ?, 0, 0, ?);");
            st.setString(1, acceptedEmail);
            st.setString(2, groupName);
            st.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            st.executeUpdate();
            st.close();
            return new StandardResponse("success", "Successfully added member to group");
        } catch (Exception f) {
            return new StandardResponse("error", "Failed to add member to group - already in group");
        }
    }

    private boolean validateAdmin(String email, String groupName, Connection c) {
        PreparedStatement st = null;
        try {
            st = c.prepareStatement("SELECT 1 FROM groups WHERE groupname = ? AND email = ? AND admin = 1;");
            st.setString(1, groupName);
            st.setString(2, email);

            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                st.close();
                rs.close();
                return false;
            }
            st.close();
            rs.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}