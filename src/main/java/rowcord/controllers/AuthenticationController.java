package rowcord.controllers;

/**
 * Created by jiaweizhang on 1/16/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import requestdata.auth.LoginRequest;
import requestdata.auth.RegisterRequest;
import responses.StandardResponse;
import rowcord.services.AuthenticationService;


@RestController
@RequestMapping("/auth/account")
public class AuthenticationController extends Controller {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse register(@RequestBody final RegisterRequest req) {
        if (req.isValid()) {
            return authenticationService.register(req.getEmail(), req.getPassword(), req.getFirstName(), req.getLastName());
        }
        return new StandardResponse(true, 1000, "json not valid");
    }


    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse login(@RequestBody final LoginRequest req) {
        if (req.isValid()) {
            return authenticationService.login(req.getEmail(), req.getPassword());

        }
        return new StandardResponse(true, 1000, "json not valid");
    }

}


