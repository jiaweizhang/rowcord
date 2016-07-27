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
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.email = email;
        registrationRequest.password = "registrationPassword";
        StdResponse stdResponse = userService.register(registrationRequest);
        assert (stdResponse.status.equals("Ok"));

        RegistrationRequest failedRegistrationRequest = new RegistrationRequest();
        failedRegistrationRequest.email = email;
        failedRegistrationRequest.password = "registrationPassword";
        StdResponse failedStdResponse = userService.register(failedRegistrationRequest);
        assert (failedStdResponse.status.equals("Bad"));
    }

    @Test
    public void TestUserLogin() {
        String email = "login" + UUID.randomUUID().toString() + "@gmail.com";
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.email = email;
        registrationRequest.password = "loginPassword";
        StdResponse stdResponse = userService.register(registrationRequest);
        assert (stdResponse.status.equals("Ok"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = email;
        loginRequest.password = "loginPassword";
        StdResponse loginResponse = userService.login(loginRequest);
        assert (loginResponse.status.equals("Ok"));

        LoginRequest failedLoginRequest = new LoginRequest();
        failedLoginRequest.email = email;
        failedLoginRequest.password = "incorrectLoginPassword";
        StdResponse failedLoginResponse = userService.login(failedLoginRequest);
        assert (failedLoginResponse.status.equals("Bad"));
    }
}
