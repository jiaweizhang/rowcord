import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rowcord.Application;
import rowcord.models.requests.LoginRequest;
import rowcord.models.requests.RegistrationRequest;
import rowcord.models.responses.StdResponse;
import rowcord.services.UserService;

import java.util.UUID;


/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void TestUserRegistration() {
        String email = "registration" + UUID.randomUUID().toString() + "@gmail.com";
        RegistrationRequest registrationRequest = new RegistrationRequest(email, "registrationPassword");
        StdResponse stdResponse = userService.register(registrationRequest);
        assert (stdResponse.error == false);

        RegistrationRequest failedRegistrationRequest = new RegistrationRequest(email, "registrationPassword");
        StdResponse failedStdResponse = userService.register(failedRegistrationRequest);
        assert (failedStdResponse.error == true);
    }

    @Test
    public void TestUserLogin() {
        String email = "login" + UUID.randomUUID().toString() + "@gmail.com";
        RegistrationRequest registrationRequest = new RegistrationRequest(email, "loginPassword");
        StdResponse stdResponse = userService.register(registrationRequest);
        assert (stdResponse.error == false);

        LoginRequest loginRequest = new LoginRequest(email, "loginPassword");
        StdResponse loginResponse = userService.login(loginRequest, "127.0.0.1");
        assert (loginResponse.error == false);

        LoginRequest failedLoginRequest = new LoginRequest(email, "incorrectLoginPassword");
        StdResponse failedLoginResponse = userService.login(failedLoginRequest, "127.0.0.1");
        assert (failedLoginResponse.error == true);
    }
}
