/**
 * Created by jiawe on 1/18/2016.
 */
myApp.controller('registerController', ['httpService', '$scope', '$http', function (httpService, $scope, $http) {
    $scope.email;
    $scope.password;
    $scope.register = function () {
        console.log("sending request");
        var data = {
            "email": $scope.email,
            "password": $scope.password
        };
        console.log(data.toString());
        httpService.register(data).then(function (response) {
            console.log(response);
            $scope.email = "";
            $scope.password = "";
        })
    }
}]);

