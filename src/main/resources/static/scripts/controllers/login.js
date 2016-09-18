/**
 * Created by alanguo on 8/10/16.
 */
angular.module('rowcordApp')
    .controller('loginController', ['$scope', '$rootScope', '$location',
        function ($scope, $rootScope, $location) {

            console.log("Login Controller");
            $scope.email;
            $scope.password;

            $scope.login = function () {
                console.log("sending login request");
                var data = {
                    "email": $scope.email,
                    "password": $scope.password
                };
                console.log("logging in with email: " + $scope.email
                    + " & pw: " + $scope.password);
                $location.path('/');
                //Auth.setUser($scope.email);
                //httpService.login(data).then(function (response) {
                //    console.log(response);
                //    var token = response.data.data.token;
                //    console.log(token);
                //    if (token != null) {
                //        console.log("redirecting to home");
                //        $cookies.put("Authorization", 'Bearer ' + token);
                //        $rootScope.loggedIn = true;
                //        $rootScope.loggedInUser = $scope.email;
                //        $scope.email = "";
                //        $scope.password = "";
                //        $location.path("/");
                //    } else {
                //        console.log("password incorrect");
                //    }
                //})
            }
        }]);