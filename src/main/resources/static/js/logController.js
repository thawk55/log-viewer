app.controller('logsController', function($scope, $rootScope, logFactory) {
    $rootScope.route = 'logs';

    $scope.searchTerm = '';
    $scope.logs = [];
    $scope.search = function(){
        logFactory.getLogs($scope.searchTerm).then(function(logs){
            $scope.logs = logs;
        }, function(err){
            console.error(err);
        });
    };
});
