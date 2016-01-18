package rowcord.controllers;

import responses.StandardResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utilities.WeightAdjuster;


/**
 * Created by jiawe on 1/16/2016.
 */

@RestController
@RequestMapping("/api")
public class ConverterController {

    public ConverterController() {
        System.out.println("init RestController");
    }

    @RequestMapping(value = "/weightadjust",
            method = RequestMethod.GET)
    @ResponseBody
    public StandardResponse test(@RequestParam(value = "lbs", defaultValue = "0") double lbs, @RequestParam(value = "secs", defaultValue = "0") double secs, @RequestParam(value = "meters", defaultValue = "0") double meters) {
        return new StandardResponse("success", "successfully adjusted", WeightAdjuster.adjust(lbs, secs, meters));
    }

}
