package rowcord.controllers;

/**
 * Created by jiaweizhang on 1/30/16.
 */

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requestdata.ergworkout.ErgWorkoutRequest;
import responses.StandardResponse;
import rowcord.services.ErgWorkoutService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/ergworkouts")
public class ErgWorkoutController {

    @Autowired
    private ErgWorkoutService ergWorkoutService;

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse addWorkout(@RequestBody final ErgWorkoutRequest req, final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId = Integer.parseInt(claims.get("userId").toString());
        if (req.isValid()) {
            return ergWorkoutService.addWorkout(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }


}