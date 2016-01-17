package rowcord.controllers;

import responses.JsonResponse;
import responses.WeightAdjustedResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import rowcord.Test;
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

    @RequestMapping(value = "/addTest",
            method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    @ResponseBody
    public JsonResponse addTest(@RequestBody final Test test) {
        return new JsonResponse("OK", "POST");
    }

    @RequestMapping(value = "/addTest",
            method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getTest() {
        return new JsonResponse("OK", "GET");
    }
}
