var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/logs', {
            templateUrl: '../views/logs.html',
            controller: 'logsController',
            controllerAs: 'ctrl'
        })
        .when('/add', {
            templateUrl: '../views/add.html',
            controller: 'addController',
            controllerAs: 'ctrl'
        })
        .otherwise({
            redirectTo: '/logs'
        });
});

