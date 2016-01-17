package rowcord.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import responses.Test;

@RestController
public class TestController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/users")
    public Test test(@RequestParam(value = "fname", defaultValue = "") String fName, @RequestParam(value = "lname", defaultValue = "") String lName) {
        return new Test(counter.incrementAndGet(),
                fName, lName);

    }
}


