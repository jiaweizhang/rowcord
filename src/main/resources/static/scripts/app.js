/**
 * Created by alanguo on 8/10/16.
 */
var rowcordApp = angular.module('rowcordApp', ['ngRoute']);
rowcordApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '../views/home.html',
            controller: 'homeCtrl'
        })
        .when('/login', {
            templateUrl: '../views/login.html',
            controller: 'loginCtrl'
        })
        //.when('/resume', {
        //    templateUrl: '../views/resume.html',
        //    controller: 'resumeCtrl'
        //})
        //.when('/contact', {
        //    templateUrl: '../views/contact.html',
        //    controller: 'contactCtrl'
        //})
        .otherwise({
            templateUrl: '../views/home.html',
            controller: 'homeCtrl'
        })
});