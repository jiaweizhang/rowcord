import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rowcord.Application;
import rowcord.models.requests.GroupCreationRequest;
import rowcord.models.requests.GroupSearchRequest;
import rowcord.models.requests.InviteUserRequest;
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

    private long userId1;

    @Before
    public void Setup() {
        String email = "email" + UUID.randomUUID().toString() + "@gmail.com";
        RegistrationRequest r1 = new RegistrationRequest(email, "password");
        RegistrationResponse registerResponse = (RegistrationResponse) userService.register(r1);
        userId1 = registerResponse.userId;
    }

    @Test
    public void TestGroupCreation() {
        String groupName = "groupName" + UUID.randomUUID().toString();
        GroupCreationRequest r = new GroupCreationRequest(groupName, "desc", 1);
        r.userId = userId1;
        StdResponse successResponse = groupService.createGroup(r);
        assert (successResponse.success);
        // TODO check for insertion status

        GroupCreationRequest r2 = new GroupCreationRequest(groupName, "desc", 1);
        StdResponse duplicateResponse = groupService.createGroup(r2);
        assert (!duplicateResponse.success);
        assert (duplicateResponse.message.equals("Group name already exists"));

        GroupCreationRequest r3 = new GroupCreationRequest(groupName + "1", "desc", 4);
        StdResponse invalidGroupTypeIdResponse = groupService.createGroup(r3);
        assert (!invalidGroupTypeIdResponse.success);
        assert (invalidGroupTypeIdResponse.message.equals("Group type not valid"));
    }

    @Test
    public void TestInviteMembers() {
        String groupName = "groupName" + UUID.randomUUID().toString();
        GroupCreationRequest r = new GroupCreationRequest(groupName, "desc", 1);
        r.userId = userId1;
        GroupCreationResponse successResponse = (GroupCreationResponse) groupService.createGroup(r);
        assert (successResponse.success);

        InviteUserRequest am = new InviteUserRequest(successResponse.groupId, Collections.singletonList(userId1));
        StdResponse successAddResponse = groupService.inviteUsers(am);
        assert (successAddResponse.success);

        InviteUserRequest am2 = new InviteUserRequest(successResponse.groupId, Collections.singletonList((long) 1000000));
        StdResponse invalidUserId = groupService.inviteUsers(am2);
        assert (invalidUserId.message.equals("User 1000000 does not exist"));

        InviteUserRequest am3 = new InviteUserRequest(successResponse.groupId, Collections.singletonList(userId1));
        StdResponse duplicateAddResponse = groupService.inviteUsers(am3);
        assert (duplicateAddResponse.message.equals("User " + userId1 + " is already has an invitation"));
    }

    @Test
    public void TestGroupSearch() {
        String groupName = "groupName" + UUID.randomUUID().toString();

        // open group
        GroupCreationRequest r = new GroupCreationRequest(groupName + "1", "desc", 1);
        r.userId = userId1;
        StdResponse successResponse = groupService.createGroup(r);
        assert (successResponse.success);

        // closed group
        GroupCreationRequest r2 = new GroupCreationRequest(groupName + "2", "desc", 2);
        r2.userId = userId1;
        StdResponse successResponse2 = groupService.createGroup(r2);
        assert (successResponse2.success);

        // this one will not be found because it is a "Secret" group
        GroupCreationRequest r3 = new GroupCreationRequest(groupName + "3", "desc", 3);
        r3.userId = userId1;
        StdResponse successResponse3 = groupService.createGroup(r3);
        assert (successResponse3.success);

        GroupSearchRequest s = new GroupSearchRequest(groupName);
        GroupSearchResponse searchResponse = groupService.searchGroups(s);
        assert (searchResponse.searchResults.keySet().size() == 2);
    }
}
