package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rowcord.services.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RestController
@RequestMapping("/api/admin")
public class AdminController extends Controller {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/dbup",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity dbup(final HttpServletRequest request) throws IOException {
        return wrap(adminService.upgradeDb());
    }

}