package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.requests.GroupSearchRequest;
import rowcord.models.requests.InviteUserRequest;
import rowcord.services.GroupService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RestController
@RequestMapping("/api/groups")
public class GroupController extends Controller {

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity createGroup(@RequestBody final GroupCreationRequest req, final HttpServletRequest request) {
        return wrap(groupService.createGroup(req));
    }

    @RequestMapping(value = "/invite",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity inviteUsers(@RequestBody final InviteUserRequest req, final HttpServletRequest request) {
        return wrap(groupService.inviteUsers(req));
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity searchGroups(@RequestBody final GroupSearchRequest req, final HttpServletRequest request) {
        return wrap(groupService.searchGroups(req));
    }
}
