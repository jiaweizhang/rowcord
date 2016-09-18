/**
 * Created by alanguo on 8/10/16.
 */
var rowcordApp = angular.module('rowcordApp', ['ngRoute']);
rowcordApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
        .when('/login', {
            templateUrl: '../views/login.html',
            controller: 'loginController'
        })
        .when('/groups', {
            templateUrl: '../views/groups.html',
            controller: 'groupsController'
        })
        .when('/logout', {
            templateUrl: '../views/logout.html',
            controller: 'logoutController'
        })
        .otherwise({
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
});