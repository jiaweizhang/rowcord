package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import databases.JDBC;
import org.springframework.web.bind.annotation.*;
import requestdata.RegisterData;
import responses.JsonResponse;
import responses.Test;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse register(@RequestBody final RegisterData rd) {
        return new JsonResponse(rd.getEmail(), rd.getPassword());
    }

    @RequestMapping("/login")
    public Test login() {
        JDBC pg = new JDBC();
        String output = pg.connect();
        return new Test(2, "login", "login");

    }

    @RequestMapping("/logout")
    public Test logout() {
        return new Test(2, "logout", "logout");

    }
}


