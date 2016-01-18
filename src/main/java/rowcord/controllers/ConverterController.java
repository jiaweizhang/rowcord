package rowcord.controllers;

import responses.WeightAdjustedResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import responses.Test;
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
    public WeightAdjustedResponse test(@RequestParam(value = "lbs", defaultValue = "0") double lbs, @RequestParam(value = "secs", defaultValue = "0") double secs, @RequestParam(value = "meters", defaultValue = "0") double meters) {
        return WeightAdjuster.adjust(lbs, secs, meters);
    }

}
