package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import org.springframework.web.bind.annotation.*;
import requestdata.LoginData;
import requestdata.RegisterData;
import responses.JsonResponse;
import responses.LoginResponse;
import responses.Test;
import rowcord.processes.AccountProcess;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse register(@RequestBody final RegisterData rd) {
        AccountProcess ap = new AccountProcess(rd.getEmail(), rd.getPassword());
        return ap.register();
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
    public Test logout() {
        return new Test(2, "logout", "logout");

    }
}


