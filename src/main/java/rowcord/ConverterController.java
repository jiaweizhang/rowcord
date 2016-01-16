package rowcord;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Created by jiawe on 1/16/2016.
 */

@RestController
public class ConverterController {

    public ConverterController() {
        System.out.println("init RestController");
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
