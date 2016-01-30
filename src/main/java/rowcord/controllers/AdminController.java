package rowcord.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import responses.StandardResponse;
import rowcord.services.AdminService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 1/23/16.
 */

@RestController
@RequestMapping("/admin")
public class AdminController extends Controller{
    @Autowired
    private AdminService adminService;

    @RequestMapping(
            value = "/init",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse init(final HttpServletRequest request) {
        if (isSuperAdmin(request)) {
            return adminService.init();
        }
        return new StandardResponse(true, 3999, "not superadmin");
    }

}
