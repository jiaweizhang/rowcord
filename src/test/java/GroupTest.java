import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rowcord.Application;
import rowcord.models.requests.AddMemberRequest;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.requests.GroupSearchRequest;
import rowcord.models.requests.RegistrationRequest;
import rowcord.models.responses.GroupCreationResponse;
import rowcord.models.responses.GroupSearchResponse;
import rowcord.models.responses.RegistrationResponse;
import rowcord.models.responses.StdResponse;
import rowcord.services.GroupService;
import rowcord.services.UserService;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by Jiawei on 7/26/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class GroupTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Test
    public void TestGroupCreation() {
        String groupName = "groupName" + UUID.randomUUID().toString();
        GroupCreationRequest r = new GroupCreationRequest(groupName, "desc", 1);
        StdResponse successResponse = groupService.createGroup(r);
        assert (successResponse.status.equals("Ok"));

        GroupCreationRequest r2 = new GroupCreationRequest(groupName, "desc", 1);
        StdResponse duplicateResponse = groupService.createGroup(r2);
        assert (duplicateResponse.status.equals("Bad"));
        assert (duplicateResponse.message.equals("Group name already exists"));

        GroupCreationRequest r3 = new GroupCreationRequest(groupName + "1", "desc", 4);
        StdResponse invalidGroupTypeIdResponse = groupService.createGroup(r3);
        assert (invalidGroupTypeIdResponse.status.equals("Bad"));
        assert (invalidGroupTypeIdResponse.message.equals("Group type not valid"));
    }

    @Test
    public void TestAddMembers() {
        String email = "email" + UUID.randomUUID().toString() + "@gmail.com";
        RegistrationRequest r1 = new RegistrationRequest(email, "password");
        RegistrationResponse registerResponse = (RegistrationResponse) userService.register(r1);
        String groupName = "groupName" + UUID.randomUUID().toString();
        GroupCreationRequest r = new GroupCreationRequest(groupName, "desc", 1);
        GroupCreationResponse successResponse = (GroupCreationResponse) groupService.createGroup(r);
        assert (successResponse.status.equals("Ok"));

        AddMemberRequest am = new AddMemberRequest(successResponse.groupId, Collections.singletonList(registerResponse.userId));
        StdResponse successAddResponse = groupService.addMembers(am);
        assert (successAddResponse.status.equals("Ok"));

        AddMemberRequest am2 = new AddMemberRequest(successResponse.groupId, Collections.singletonList((long) 1000000));
        StdResponse invalidUserId = groupService.addMembers(am2);
        assert (invalidUserId.message.equals("User 1000000 does not exist"));

        AddMemberRequest am3 = new AddMemberRequest(successResponse.groupId, Collections.singletonList(registerResponse.userId));
        StdResponse duplicateAddResponse = groupService.addMembers(am3);
        assert (duplicateAddResponse.message.equals("User " + registerResponse.userId + " is already in the group"));
    }

    @Test
    public void TestGroupSearch() {
        String groupName = "groupName" + UUID.randomUUID().toString();

        // open group
        GroupCreationRequest r = new GroupCreationRequest(groupName + "1", "desc", 1);
        StdResponse successResponse = groupService.createGroup(r);
        assert (successResponse.status.equals("Ok"));

        // closed group
        GroupCreationRequest r2 = new GroupCreationRequest(groupName + "2", "desc", 2);
        StdResponse successResponse2 = groupService.createGroup(r2);
        assert (successResponse2.status.equals("Ok"));

        // this one will not be found because it is a "Secret" group
        GroupCreationRequest r3 = new GroupCreationRequest(groupName + "3", "desc", 3);
        StdResponse successResponse3 = groupService.createGroup(r3);
        assert (successResponse3.status.equals("Ok"));

        GroupSearchRequest s = new GroupSearchRequest(groupName);
        GroupSearchResponse searchResponse = groupService.searchGroups(s);
        assert (searchResponse.searchResults.keySet().size() == 2);
    }
}
