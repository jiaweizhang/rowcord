package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import org.springframework.web.bind.annotation.*;
import requestdata.auth.LoginData;
import requestdata.auth.RegisterData;
import responses.StandardResponse;
import rowcord.processes.AuthenticationProcess;

@RestController
@RequestMapping("/auth/account")
public class AuthenticationController {

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse register(@RequestBody final RegisterData rd) {
        AuthenticationProcess ap = new AuthenticationProcess(rd.getEmail(), rd.getPassword());
        return ap.register();
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public StandardResponse login(@RequestBody final LoginData ld) {
        AuthenticationProcess ap = new AuthenticationProcess(ld.getEmail(), ld.getPassword());
        return ap.login();
    }
}


