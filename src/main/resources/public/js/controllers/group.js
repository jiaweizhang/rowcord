/**
 * Created by jiawe on 1/18/2016.
 */

myApp.controller('groupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {

    httpService.getGroups().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data;
    })
}]);

myApp.controller('groupcreateController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    $scope.groupName;
    $scope.groupDescription;
    $scope.create = function () {
        console.log("sending request");
        var data = {
            "groupName": $scope.groupName,
            "groupDescription": $scope.groupDescription
        };
        console.log(data.toString());
        httpService.createGroup(data).then(function (response) {
            console.log(response);
            $scope.groupName = "";
            $scope.groupDescription = "";
        })
    }
}]);

myApp.controller('mygroupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {

    httpService.getMembership().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data;
    })
}]);


