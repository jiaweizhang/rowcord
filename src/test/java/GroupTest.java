import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rowcord.Application;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.responses.StdResponse;
import rowcord.services.GroupService;

import java.util.UUID;

/**
 * Created by Jiawei on 7/26/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class GroupTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void TestGroupCreation() {
        String groupName = "groupName" + UUID.randomUUID().toString();
        GroupCreationRequest r = new GroupCreationRequest();
        r.groupName = groupName;
        r.groupDescription = "some group description";
        r.groupTypeId = 1;
        StdResponse successResponse = groupService.createGroup(r);
        assert (successResponse.status.equals("Ok"));

        GroupCreationRequest r2 = new GroupCreationRequest();
        r2.groupName = groupName;
        r2.groupDescription = "some group description";
        r2.groupTypeId = 1;
        StdResponse duplicateResponse = groupService.createGroup(r2);
        assert (duplicateResponse.status.equals("Bad"));
        assert (duplicateResponse.message.equals("Group name already exists"));

        GroupCreationRequest r3 = new GroupCreationRequest();
        r3.groupName = "groupName" + UUID.randomUUID().toString();
        r3.groupDescription = "some group description";
        r3.groupTypeId = 4;
        StdResponse invalidGroupTypeIdResponse = groupService.createGroup(r3);
        assert (invalidGroupTypeIdResponse.status.equals("Bad"));
        assert (invalidGroupTypeIdResponse.message.equals("Group type not valid"));
    }
}
