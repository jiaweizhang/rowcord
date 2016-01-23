package rowcord.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requestdata.subgroup.CreateSubgroupRequest;
import responses.StandardResponse;
import rowcord.services.SubgroupService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 1/23/16.
 */

@RestController
@RequestMapping("/api/subgroups")
public class SubgroupController {

    @Autowired
    private SubgroupService subgroupService;

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse createSubgroup(@RequestBody final CreateSubgroupRequest req, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId =  Integer.parseInt(claims.get("userId").toString());
        if (req.isValid()) {
            return subgroupService.createSubgroup(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

}
