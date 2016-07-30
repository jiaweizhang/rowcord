package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rowcord.models.requests.LoginRequest;
import rowcord.models.requests.RegistrationRequest;
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

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity register(@RequestBody final RegistrationRequest req, final HttpServletRequest request) {
        return wrap(userService.register(req));
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public ResponseEntity login(@RequestBody final LoginRequest req, final HttpServletRequest request) {
        return wrap(userService.login(req, request.getRemoteAddr()));
    }
}
