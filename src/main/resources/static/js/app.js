var app = angular.module('app', ['ngRoute','ngResource', 'ui.bootstrap']);
app.config(function($routeProvider){
    $routeProvider
        .when('/logs',{
            templateUrl: '/views/logs.html',
            controller: 'logsController'
        })
        .otherwise({
            redirectTo: '/logs'
        });
});

