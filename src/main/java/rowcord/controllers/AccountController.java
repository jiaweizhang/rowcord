package rowcord.controllers;

/**
 * Created by jiawe on 1/16/2016.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rowcord.Test;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @RequestMapping("/register")
    public Test register() {
        return new Test(1, "register", "register");
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


