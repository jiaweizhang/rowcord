package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rowcord.Controller;
import rowcord.models.LoginRequest;
import rowcord.models.RegistrationRequest;
import rowcord.models.StdResponse;
import rowcord.services.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RestController
@RequestMapping("/api/users")
public class UserController extends Controller {

    @Autowired
    private UserService userService;

    /**
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StdResponse register(@RequestBody final RegistrationRequest req, final HttpServletRequest request) {
        return new StdResponse("Ok", "Successfully registered user", userService.register(req));
    }

    /**
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StdResponse login(@RequestBody final LoginRequest req, final HttpServletRequest request) {
        return new StdResponse("Ok", "Successfully logged in", userService.login(req));
    }
}
