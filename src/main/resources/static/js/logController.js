app.controller('logsController', function($scope, $rootScope, logFactory) {
    $rootScope.route = 'logs';

    $scope.searchTerm = '';
    $scope.logs = [];
    $scope.search = function(){
        logFactory.getLogs().then(function(logs){
            console.log(logs);
            $scope.logs = logs;
        }, function(err){
            console.error(err);
        });
    };
});
