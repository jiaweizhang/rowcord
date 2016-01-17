package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import databases.JDBC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rowcord.Test;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @RequestMapping("/register")
    public Test register() {
        JDBC pg = new JDBC();
        String output = pg.connect();
        return new Test(1, "message: "+ output, "register");
    }

    @RequestMapping("/login")
    public Test login() {
        return new Test(2, "login", "login");

    }

    @RequestMapping("/logout")
    public Test logout() {
        return new Test(2, "logout", "logout");

    }
}


