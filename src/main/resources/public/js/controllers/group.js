/*
 * Created by jiawe on 1/18/2016.
 */

myApp.controller('groupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {

    console.log("groups controller initialized");

    $scope.sortType = 'groupName';
    $scope.sortReverse = false;
    $scope.searchName = '';

    httpService.getGroups().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data.groups;
    });

    $scope.urlEncode = function (groupName) {
        return groupName.replace(/\s+/g, '+');
    };

    $scope.unixTimeConvert = function (unix_timestamp){
        // Create a new JavaScript Date object based on the timestamp
        // multiplied by 1000 so that the argument is in milliseconds, not seconds.
        var date = new Date(unix_timestamp);
        // Hours part from the timestamp
        var hours = date.getHours();
        // Minutes part from the timestamp
        var minutes = "0" + date.getMinutes();
        // Seconds part from the timestamp
        var seconds = "0" + date.getSeconds();

        var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        var year = date.getFullYear();
        var month = months[date.getMonth()];
        var day = date.getDate();
        return day + ' ' + month + ' ' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    }

}]);

myApp.controller('groupcreateController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    console.log("Group Create Controller");
    $scope.groupName;
    $scope.description;
    $scope.publicBool;
    $scope.create = function () {
        console.log("sending group create request");
        console.log("$scope.groupName: "+$scope.groupName);
        console.log("$scope.description: "+$scope.description);
        console.log("$scope.publicBool: "+$scope.publicBool);

        var data = {
            "groupName": $scope.groupName,
            "description": $scope.description,
            "publicBool": $scope.publicBool
        };
        httpService.createGroup(data).then(function (response) {
            console.log(response);
            $scope.groupName = "";
            $scope.description = "";
        })
    }

}]);

myApp.controller('mygroupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    //$scope.groupName;
    //$scope.groupID;
    //$scope.joinDate;
    //$scope.isAdmin;
    //$scope.isCoach;
    //$scope.groups;
    $scope.sortType = 'groupName';
    $scope.sortReverse = false;

    $scope.membership = function () {
        httpService.getMembership().then(function (response) {
            console.log(response.message);
            $scope.groups = response.data.data.groups;
            console.log("$scope.groups: "+$scope.groups);
            //$scope.groupName = response.data.group.groupName;
            //$scope.groupID = response.data.group.groupID;
            //$scope.joinDate = response.data.group.joinDate;
            //$scope.isAdmin = response.data.group.adminBool;
            //$scope.isCoach = response.data.group.coachBool;
        });
    };


    $scope.urlEncode = function (groupName) {
        return groupName.replace(/\s+/g, '+');
    };

    $scope.unixTimeConvert = function (unix_timestamp){
        // Create a new JavaScript Date object based on the timestamp
    // multiplied by 1000 so that the argument is in milliseconds, not seconds.
        var date = new Date(unix_timestamp);
    // Hours part from the timestamp
        var hours = date.getHours();
    // Minutes part from the timestamp
        var minutes = "0" + date.getMinutes();
    // Seconds part from the timestamp
        var seconds = "0" + date.getSeconds();

        var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        var year = date.getFullYear();
        var month = months[date.getMonth()];
        var day = date.getDate();
        return day + ' ' + month + ' ' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    }

}]);


myApp.controller('groupdetailController', ['httpService', '$scope', '$http', '$routeParams', function (httpService, $scope, $http, $routeParams) {
    $scope.sortType = 'lastName';
    $scope.sortReverse = false;

    console.log('groupdetailcontroller initialized');
    console.log("sending request");
    console.log($routeParams);
    var data = {
        "groupId": $routeParams.groupId
    };
    console.log(data.toString());
    httpService.getGroupById(data.groupId).then(function (response) {
        console.log(response);
        $scope.group = response.data.data.group;
        $scope.members = response.data.data.members;
    });

    $scope.unixTimeConvert = function (unix_timestamp){
        // Create a new JavaScript Date object based on the timestamp
        // multiplied by 1000 so that the argument is in milliseconds, not seconds.
        var date = new Date(unix_timestamp);
        // Hours part from the timestamp
        var hours = date.getHours();
        // Minutes part from the timestamp
        var minutes = "0" + date.getMinutes();
        // Seconds part from the timestamp
        var seconds = "0" + date.getSeconds();

        var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        var year = date.getFullYear();
        var month = months[date.getMonth()];
        var day = date.getDate();
        return day + ' ' + month + ' ' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
    }
}]);


