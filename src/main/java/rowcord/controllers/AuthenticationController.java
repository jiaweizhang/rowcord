package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import org.springframework.web.bind.annotation.*;
import requestdata.LoginData;
import requestdata.RegisterData;
import responses.JsonResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import rowcord.processes.AccountProcess;

@RestController
@RequestMapping("/auth/account")
public class AuthenticationController {

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public RegisterResponse register(@RequestBody final RegisterData rd) {
        AccountProcess ap = new AccountProcess(rd.getEmail(), rd.getPassword());
        return ap.register();
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse test() {
        return new JsonResponse("one", "two");
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public LoginResponse login(@RequestBody final LoginData ld) {
        AccountProcess ap = new AccountProcess(ld.getEmail(), ld.getPassword());
        return ap.login();
    }

    @RequestMapping("/logout")
    public JsonResponse logout() {
        return new JsonResponse("logout", "logout");

    }
}


