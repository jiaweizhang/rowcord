/**
 * Created by jiawe on 1/18/2016.
 */
myApp.controller('registerController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    $scope.email;
    $scope.password;
    $scope.firstName;
    $scope.lastName;
    $scope.register = function () {
        console.log("sending request");
        var data = {
            "email": $scope.email,
            "password": $scope.password,
            "firstName": $scope.firstName,
            "lastName": $scope.lastName
        };
        console.log(data);
        httpService.register(data).then(function (response) {
            console.log(response);
            $scope.email = "";
            $scope.password = "";
        })
    }
}]);

