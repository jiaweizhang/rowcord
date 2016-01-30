/**
 * Created by jiaweizhang on 1/18/2016.
 */
myApp.controller('loginController', ['httpService', '$scope', '$cookies', '$rootScope', '$location',
    function (httpService, $scope, $cookies, $rootScope, $location) {

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
                if (token != null) {
                    console.log("redirecting to home");
                    $cookies.put("Authorization", 'Bearer ' + token);
                    $rootScope.loggedUser = 1;
                    $rootScope.loggedInUser = $scope.email;
                    $scope.email = "";
                    $scope.password = "";
                    $location.path("/");
                } else {
                    console.log("password incorrect");
                }
            })
        }

    }]);