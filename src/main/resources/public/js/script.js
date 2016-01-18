/**
 * Created by jiawe on 1/17/2016.
 */

angular.module('myApp', [])
    .controller('myCtrl', function ($scope, $http) {
        $scope.registerEmail = "";
        $scope.registerPassword = "";
        $scope.register = function () {
            console.log("sending request");
            var data = {
                "email": $scope.registerEmail,
                "password": $scope.registerPassword
            };
            console.log(data.toString());
            $http({
                url: "acc/account/register",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type":"application/json"
                }
            }).success(function (data, status) {
                console.log(data);
            });
        }

        $scope.loginEmail = "";
        $scope.loginPassword = "";
        $scope.login = function () {
            console.log("sending request");
            var data = {
                "email": $scope.loginEmail,
                "password": $scope.loginPassword
            };
            console.log(data.toString());
            $http({
                url: "acc/account/login",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type":"application/json"
                }
            }).success(function (data, status) {
                console.log(data);
            });
        }
    })