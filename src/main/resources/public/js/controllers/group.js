/**
 * Created by jiawe on 1/18/2016.
 */

myApp.controller('groupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {

    console.log("groups controller initialized");
    httpService.getGroups().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data;
    });

    $scope.urlEncode = function(groupName) {
        return groupName.replace(/\s+/g, '+');
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

    httpService.getMembership().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data;
    })

    $scope.urlEncode = function(groupName) {
        return groupName.replace(/\s+/g, '+');
    }
}]);


myApp.controller('groupdetailController', ['httpService', '$scope', '$http', '$routeParams', function (httpService, $scope, $http, $routeParams) {

    console.log('groupdetailcontroller inisitalized');
    console.log("sending request");
    console.log($routeParams);
    var data = {
        "groupName": $routeParams.groupName
    };
    console.log(data.toString());
    httpService.getGroupDetail(data).then(function (response) {
        console.log(response);
        $scope.group = response.data.data;
    })
}]);


