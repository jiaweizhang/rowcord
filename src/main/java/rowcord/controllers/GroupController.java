package rowcord.controllers;

/**
 * Created by jiaweizhang on 1/18/2016.
 */
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requestdata.group.*;
import responses.StandardResponse;
import rowcord.services.GroupService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse createGroup(@RequestBody final CreateGroupRequest req, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId =  Integer.parseInt(claims.get("userId").toString());
        if (req.isValid()) {
            return groupService.createGroup(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }


    @RequestMapping(
            value = "/memberships",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getMemberships(final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId =  Integer.parseInt(claims.get("userId").toString());
        return groupService.getMemberships(userId);
    }

    @RequestMapping(
            value = "/public",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getPublics() {
        return groupService.getPublics();
    }

    @RequestMapping(
            value = "/{groupId}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getGroupById(@PathVariable int groupId, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId =  Integer.parseInt(claims.get("userId").toString());
        return groupService.getGroupById(userId, groupId);
    }

    @RequestMapping(
            value = "/apply",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse apply(@RequestBody final GroupApplicationRequest req, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId =  Integer.parseInt(claims.get("userId").toString());
        if (req.isValid()) {
            return groupService.apply(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }


/*

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
    */
}
