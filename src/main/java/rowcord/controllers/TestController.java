package rowcord.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rowcord.models.StdResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping(value = "/get",
            method = RequestMethod.GET)
    @ResponseBody
    public StdResponse register(final HttpServletRequest request) {
        if (1 == 1) throw new IllegalArgumentException("foo==null");
        return new StdResponse("Ok", "Successful get");
    }

}
