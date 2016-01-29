/**
 * Created by jiaweizhang on 1/18/2016.
 */

var myApp = angular
    .module('myApp', ['ngRoute', 'ngCookies']);

myApp.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
            templateUrl: 'pages/home.html',
            controller: 'homeController'
        })

        .when('/about', {
            templateUrl: 'pages/about.html',
            controller: 'aboutController'
        })

        .when('/register', {
            templateUrl: 'pages/register.html',
            controller: 'registerController'
        })

        .when('/login', {
            templateUrl: 'pages/login.html',
            controller: 'loginController'
        })

        .when('/groups', {
            templateUrl: 'pages/groups.html',
            controller: 'groupsController'
        })

        .when('/groups/create', {
            templateUrl: 'pages/groupcreate.html',
            controller: 'groupcreateController'
        })

        .when('/groups/mygroups', {
            templateUrl: 'pages/mygroups.html',
            controller: 'mygroupsController'
        })

        .when('/groups/group/:groupName', {
            templateUrl: 'pages/groupdetail.html',
            controller: 'groupdetailController'
        })


        .otherwise( {
            redirectTo: "/"
        });
})
    .run(function($rootScope, $location, $cookies, $http) {
        console.log("running .run redirect");
        $rootScope.userAuth = $cookies.get('Authorization');
        //$rootScope.globals = $cookies.get('globals') || {};
        console.log("$rootScope.userAuth: "+ $rootScope.userAuth);
        if ($rootScope.userAuth !== undefined){ // figure out LOGIC
            console.log("$rootScope.userAuth:"+$rootScope.userAuth);
            $http.defaults.headers.common['Authorization'] = 'Bearer ' + $rootScope.userAuth;
        }
        //if ($rootScope.globals.currentUser) {
        //    console.log("$rootScope.globals.currentUser.authdata:"+$rootScope.globals.currentUser.authdata);
        //    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        //}
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            console.log("NEW ROUTE CHANGE");
            // redirect to login page if not logged in and trying to access a restricted page
            var unrestrictedPages = ['/login', '/register', '/'];
            var restrictedPage = unrestrictedPages.indexOf($location.path()) === -1;
            console.log("restpage:" + restrictedPage);
            //var restrictedPage = $.inArray($location.path(), ['/login', '/register', '/']) === -1;
            var loggedIn = $rootScope.userAuth !== undefined;
            console.log("loggedin: "+loggedIn);
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
});

myApp.controller('mainController', ['httpService', '$scope', '$http', '$window', '$cookies', function (httpService, $scope, $http, $window, $cookies) {
    console.log("mainController");

    $scope.logout = function () {
        //$window.sessionStorage.removeItem("accessToken");
        $cookies.remove("Authorization");
    }

}]);

/*myApp.config( ['$routeProvider', function($routeProvider) {'myApp', ['ngRoute', 'ngCookies']}] )
    .run( function($rootScope, $location) {

        // register listener to watch route changes
        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            if ( $rootScope.loggedUser == null ) {
                $location.path( "/login" );

            }
        });
    });*/

myApp.service('httpService', function ($http, $window, $cookies) {
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
                    "Authorization": $cookies.get("Authorization")
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
                    "Authorization": $cookies.get("Authorization")
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        },

        getGroups: function () {
            return $http({
                url: "api/groups",
                method: "GET",
                headers: {
                    "Authorization": $cookies.get("Authorization")
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        },

        getGroupDetail: function (data) {
            return $http({
                url: "api/groups/groupdetail",
                method: "POST",
                data: data,
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": $cookies.get("Authorization")
                }
            }).success(function (data, status) {
                console.log(data);
                return data;
            });
        }
    };
});