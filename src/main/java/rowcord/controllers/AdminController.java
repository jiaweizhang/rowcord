package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import responses.StandardResponse;
import rowcord.services.AdminService;

/**
 * Created by jiaweizhang on 1/23/16.
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(
            value = "/init",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse init() {
        return adminService.init();
    }

}
