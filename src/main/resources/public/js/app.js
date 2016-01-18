/**
 * Created by jiawe on 1/18/2016.
 */

var myApp = angular
    .module('myApp', ['ngRoute']);

myApp.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
            templateUrl: 'pages/home.html',
            controller: 'homeController'
        })

        .when('/register', {
            templateUrl: 'pages/register.html',
            controller: 'registerController'
        })

        .when('/login', {
            templateUrl: 'pages/login.html',
            controller: 'loginController'
        })

        .when('/groups/create', {
            templateUrl: 'pages/groupcreate.html',
            controller: 'groupcreateController'
        })

        .when('/groups/mygroups', {
            templateUrl: 'pages/mygroups.html',
            controller: 'mygroupsController'
        });
});

myApp.controller('mainController', ['httpService', '$scope', '$http', '$window', function (httpService, $scope, $http, $window) {
    console.log("mainController");
    $scope.logout = function() {
        $window.sessionStorage.removeItem("accessToken");
    }
}]);

myApp.service('httpService', function ($http, $window) {
    return {
        register: function (data) {
            return $http({
                url: "auth/account/register",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        },

        login: function (data) {
            return $http({
                url: "auth/account/login",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type": "application/json"
                }
            }).success(function (data, status) {
                console.log(data);
                console.log(data.data.token);
                return data;
            });
        },

        createGroup: function (data) {
            return $http({
                url: "api/groups/create",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": $window.sessionStorage.accessToken
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        },

        getMembership: function () {
            return $http({
                url: "api/groups/memberships",
                method: "GET",
                headers: {
                    "Authorization": $window.sessionStorage.accessToken
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        }
    };
});