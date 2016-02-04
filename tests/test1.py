import unittest
import json
import requests


# unittest.TestLoader.sortTestMethodsUsing = lambda _, x, y: cmp(y, x)


class TestTagTestCases(unittest.TestCase):
    def setUp(self):
        self.baseUrl = 'http://localhost:8080/'
        self.headers = {'Accept': 'application/json', "Content-Type": "application/json"}
        self.adminHeaders = {
            'Accept': 'application/json',
            "Content-Type": "application/json",
            "Authorization": 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlcklkIjoxfQ.k-0AcWZvrko72dyoDtnnwAq9t2bixLhzvICFHFYXrhg'
        }
        self.user1Headers = {
            'Accept': 'application/json',
            "Content-Type": "application/json",
            "Authorization": 'Bearer ' + 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwidXNlcklkIjoyfQ.72jhuLh_d3I6_7mjEOmEeFiNS4_SkXt5oNJV7RFqi8Y'

        }

        self.resetUrl = 'admin/init'
        self.registerUrl = 'auth/account/register'
        self.loginUrl = 'auth/account/login'
        self.groupUrl = 'api/groups'
        self.subgroupUrl = 'api/subgroups'

    def test0010_ResetDatabase(self):
        r = requests.get(self.baseUrl + self.resetUrl)
        decoded = r.json()
        print "reset"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0020_Login(self):
        request = {
            "email": "admin@admin.com",
            "password": "admin",
        }
        r = requests.post(self.baseUrl + self.loginUrl, data=json.dumps(request), headers=self.headers)
        decoded = r.json()
        print "login"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0030_CreateGroup(self):
        request = {
            "groupName": "first group",
            "description": "first group description",
            "publicBool": False
        }
        r = requests.post(self.baseUrl + self.groupUrl, data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "create group"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0040_CreateGroup2(self):
        request = {
            "groupName": "second",
            "description": "sec group description",
            "publicBool": True
        }
        r = requests.post(self.baseUrl + self.groupUrl, data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "create group2"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0050_CreateUser1(self):
        request = {
            "email": "user1@email.com",
            "password": "password",
            "firstName": "User1",
            "lastName": "User1"
        }
        r = requests.post(self.baseUrl + self.registerUrl, data=json.dumps(request), headers=self.headers)
        decoded = r.json()
        print 'create user1'
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0060_LoginUser1(self):
        request = {
            "email": "user1@email.com",
            "password": "password",
        }
        r = requests.post(self.baseUrl + self.loginUrl, data=json.dumps(request), headers=self.headers)
        decoded = r.json()
        print "login user1"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0070_CreateGroup3(self):
        request = {
            "groupName": "third group",
            "description": "third group description",
            "publicBool": False
        }
        r = requests.post(self.baseUrl + self.groupUrl, data=json.dumps(request), headers=self.user1Headers)
        decoded = r.json()
        print "create group3"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0080_CreateGroup4(self):
        request = {
            "groupName": "fourth",
            "description": "fourth group description",
            "publicBool": True
        }
        r = requests.post(self.baseUrl + self.groupUrl, data=json.dumps(request), headers=self.user1Headers)
        decoded = r.json()
        print "create group4"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0090_GetAdminMembership(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/memberships', headers=self.adminHeaders)
        decoded = r.json()
        print "get admin memberships"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0100_GetUser1Membership(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/memberships', headers=self.user1Headers)
        decoded = r.json()
        print "get user1 memberships"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0110_GetAllGroups(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/type=all', headers=self.adminHeaders)
        decoded = r.json()
        print "get all groups"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0120_GetFirstGroup(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/1', headers=self.adminHeaders)
        decoded = r.json()
        print "get 1st group"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0130_GetFourthGroup(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/4', headers=self.adminHeaders)
        decoded = r.json()
        print "get 4th group"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0140_AdminApplyToGroup3(self):
        request = {
            "groupId": 3
        }
        r = requests.post(self.baseUrl + self.groupUrl + "/apply", data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "apply to group 3 by admin"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0150_User1GetApplications(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/apply/3', headers=self.user1Headers)
        decoded = r.json()
        print "get applications"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0160_User1AcceptAdminToGroup3(self):
        request = {
            "groupId": 3,
            "userId": 1
        }
        r = requests.post(self.baseUrl + self.groupUrl + "/accept", data=json.dumps(request), headers=self.user1Headers)
        decoded = r.json()
        print "accept admin to group 3 by user1"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0170_GetAdminMembershipAfterJoiningGroup3(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/memberships', headers=self.adminHeaders)
        decoded = r.json()
        print "get admin memberships"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False
        assert decoded['data']['groups'][2]["groupId"] == 3

    def test0180_User1PromoteAdminToCoachAdmin(self):
        request = {
            "userId": 1,
            "groupId": 3,
            "adminBool": True,
            "coachBool": True
        }
        r = requests.post(self.baseUrl + self.groupUrl + "/role", data=json.dumps(request), headers=self.user1Headers)
        decoded = r.json()
        print "change user1 role"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0190_AdminKickUser1FromGroup3(self):
        request = {
            "userId": 2,
            "groupId": 3,
        }
        r = requests.delete(self.baseUrl + self.groupUrl + "/kick", data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "change user1 role"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0200_Get3rdGroupAgain(self):
        r = requests.get(self.baseUrl + self.groupUrl + '/1', headers=self.adminHeaders)
        decoded = r.json()
        print "get 3rd group"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0210_CreateSubgroupIn1stGroup(self):
        request = {
            "groupId": 1,
            "subgroupName": "first subgroup",
            "description": "This is the first subgroup"
        }
        r = requests.post(self.baseUrl + self.subgroupUrl, data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "create subgroup in 1st group"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0220_User1ApplyToGroup1(self):
        request = {
            "groupId": 1
        }
        r = requests.post(self.baseUrl + self.groupUrl + "/apply", data=json.dumps(request), headers=self.user1Headers)
        decoded = r.json()
        print "apply to group 1 by user1"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0230_AdminAcceptUser1ToGroup1(self):
        request = {
            "groupId": 1,
            "userId": 2
        }
        r = requests.post(self.baseUrl + self.groupUrl + "/accept", data=json.dumps(request), headers=self.adminHeaders)
        decoded = r.json()
        print "accept user1 to group1 by admin"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False

    def test0240_AddUser1ToSubgroup(self):
        request = {
            "groupId": 1,
            "subgroupId": 1,
            "members": [2]
        }

        r = requests.post(self.baseUrl + self.subgroupUrl + "/members", data=json.dumps(request),
                          headers=self.adminHeaders)
        decoded = r.json()
        print "add user1 to subgroup 1"
        print json.dumps(decoded, indent=4)
        print "\n\n"
        assert decoded['error'] == False


if __name__ == '__main__':
    unittest.main()
