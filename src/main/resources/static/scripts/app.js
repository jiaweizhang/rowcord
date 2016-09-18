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

        .otherwise({
            templateUrl: '../views/home.html',
            controller: 'homeController'
        })
});