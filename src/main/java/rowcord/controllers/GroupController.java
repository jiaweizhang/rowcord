package rowcord.controllers;

/**
 * Created by jiaweizhang on 1/18/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requestdata.group.*;
import responses.StandardResponse;
import rowcord.services.GroupService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/groups")
public class GroupController extends Controller {

    @Autowired
    private GroupService groupService;

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse createGroup(@RequestBody final CreateGroupRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
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
        int userId = getUserId(request);
        return groupService.getMemberships(userId);
    }

    @RequestMapping(
            value = "/type={groupType}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getPublics(@PathVariable String groupType) {
        if (groupType.equals("public")) {
            return groupService.getPublics();
        }
        if (groupType.equals("private")) {
            return groupService.getPrivates();
        }
        if (groupType.equals("all")) {
            return groupService.getAll();
        }
        return new StandardResponse(true, 1005, "group type does not exist");
    }

    @RequestMapping(
            value = "/{groupId}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getGroupById(@PathVariable int groupId, final HttpServletRequest request) {
        int userId = getUserId(request);
        return groupService.getGroupById(userId, groupId);
    }

    @RequestMapping(
            value = "/group/{groupName}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getGroupByName(@PathVariable String groupName, final HttpServletRequest request) {
        int userId = getUserId(request);
        return groupService.getGroupByName(userId, groupName);
    }

    @RequestMapping(
            value = "/apply",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse apply(@RequestBody final GroupApplicationRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return groupService.apply(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

    @RequestMapping(
            value = "/apply/{groupId}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getApplications(@PathVariable int groupId, final HttpServletRequest request) {
        int userId = getUserId(request);
        return groupService.getApplications(userId, groupId);
    }

    @RequestMapping(
            value = "/accept",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse accept(@RequestBody final AcceptRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return groupService.accept(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

    @RequestMapping(
            value = "/kick",
            method = RequestMethod.DELETE,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse kick(@RequestBody final KickRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return groupService.kick(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

    @RequestMapping(
            value = "/role",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse changeRole(@RequestBody final RoleRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return groupService.changeRole(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

}
