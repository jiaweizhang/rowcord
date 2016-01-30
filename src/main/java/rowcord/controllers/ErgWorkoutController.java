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
import java.util.Date;

@RestController
@RequestMapping("/api/ergworkouts")
public class ErgWorkoutController extends Controller {

    @Autowired
    private ErgWorkoutService ergWorkoutService;

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse addWorkout(@RequestBody final ErgWorkoutRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return ergWorkoutService.addWorkout(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse editWorkout(@RequestBody final ErgWorkoutRequest req, final HttpServletRequest request) {
        int userId = getUserId(request);
        if (req.isValid()) {
            return ergWorkoutService.editWorkout(req, userId);
        }
        return new StandardResponse(true, 1000, "json is not valid");
    }

    @RequestMapping(
            value = "{ergworkoutId}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public StandardResponse deleteWorkout(@PathVariable int ergworkoutId, final HttpServletRequest request) {
        int userId = getUserId(request);
        return ergWorkoutService.deleteWorkout(ergworkoutId, userId);
    }

    @RequestMapping(
            value = "{ergworkoutId}",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getById(@PathVariable int ergworkoutId, final HttpServletRequest request) {
        int userId = getUserId(request);
        return ergWorkoutService.getById(ergworkoutId, userId);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET)
    public StandardResponse complexGet(
            @RequestParam(value = "begin", required = false) Date beginDate,
            @RequestParam(value = "end", required = false) Date endDate,
            @RequestParam(value = "n", required = false) Integer n,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "format", required = false) String format,
            final HttpServletRequest request) {
        final Claims claims = (Claims) request.getAttribute("claims");
        int userId = Integer.parseInt(claims.get("userId").toString());
        return ergWorkoutService.complexGet(beginDate, endDate, n, type, format, userId);
    }

    @RequestMapping(
            value = "/summary",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse getSummary(
            @RequestParam(value = "span", required = false) Integer span, final HttpServletRequest request) {
        int userId = getUserId(request);
        return ergWorkoutService.getSummary(span, userId);
    }
}