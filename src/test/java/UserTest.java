import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rowcord.Application;
import rowcord.models.RegistrationRequest;
import rowcord.models.StdResponse;
import rowcord.services.UserService;


/**
 * Created by jiaweizhang on 7/26/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void TestUserRegistration() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        StdResponse stdResponse = userService.register(registrationRequest);
        assert (stdResponse == null);
    }
}
