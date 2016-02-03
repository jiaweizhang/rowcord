/**
 * Created by alanguo on 1/29/16.
 */
//myApp.controller('logoutController', ['httpService', '$scope', '$http', '$window', '$cookies', '$rootScope',
//    function (httpService, $scope, $http, $window, $cookies, $rootScope) {
//        $scope.logout = function () {
//            //$window.sessionStorage.removeItem("accessToken");
//            $cookies.remove("Authorization");
//            $rootScope.loggedIn = false;
//            $location.path('/logout');
//        }
//    });

myApp.controller('logoutController', ['$scope','$location', '$cookies', '$rootScope',
    function($location, $cookies, $rootScope, $scope) {
        $scope.logout = function() {
            $cookies.remove("Authorization");
            $rootScope.loggedIn = false;
            $location.path('/logout');
        };
        $scope.logout();
    }

]);
