/**
 * Created by jiawe on 1/18/2016.
 */
myApp.controller('groupcreateController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    $scope.groupName;
    $scope.create = function () {
        console.log("sending request");
        var data = {
            "groupName": $scope.groupName
        };
        console.log(data.toString());
        httpService.createGroup(data).then(function (response) {
            console.log(response);
            $scope.groupName = "";
        })
    }
}]);

myApp.controller('mygroupsController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {

    httpService.getMembership().then(function (response) {
        console.log(response);
        $scope.groups = response.data.data;
    })
}]);


