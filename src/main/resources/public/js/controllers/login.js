/**
 * Created by jiawe on 1/18/2016.
 */
myApp.controller('loginController', ['httpService', '$scope', '$http', '$window', function (httpService, $scope, $http, $window) {

    console.log("logincontroller started");
    $scope.email;
    $scope.password;
    $scope.login = function () {
        console.log("sending request");
        var data = {
            "email": $scope.email,
            "password": $scope.password
        };
        httpService.login(data).then(function (response) {
            console.log(response);
            var token = response.data.data.token;
            console.log(token);
            $window.sessionStorage.accessToken = 'Bearer ' + token;
            //$http.defaults.headers.common.Authorization = 'Bearer ' + token;
            $scope.email = "";
            $scope.password = "";
        })
    }

}]);