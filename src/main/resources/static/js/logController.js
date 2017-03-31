app.controller('logsController', function($scope, $rootScope, logFactory) {
    $rootScope.route = 'logs';

    $scope.searchTerm = '';
    $scope.pagination = {logs: []};
    $scope.viewSize = 50;
    $scope.offset = 0;
    $scope.page = 1;
    $scope.search = function(){
        logFactory.getLogs($scope.searchTerm, $scope.viewSize, $scope.offset).then(function(pagination){
            $scope.pagination = pagination;
        }, function(err){
            console.error(err);
        });
    };

    $scope.nextPage = function() {
        if($scope.canGoToNextPage()){
            $scope.offset += $scope.viewSize;
            $scope.page++;
            $scope.search();
        }
    };

    $scope.prevPage = function() {
        if($scope.page != 1){
            $scope.offset -= $scope.viewSize;
            $scope.page--;
            $scope.search();
        }
    };

    $scope.canGoToNextPage = function() {
        return ($scope.offset + $scope.viewSize < $scope.pagination.totalSize);
    };

    $scope.getTopRange = function() {
        if($scope.offset + $scope.viewSize < $scope.pagination.totalSize){
            return $scope.offset + $scope.viewSize;
        } else {
            return $scope.pagination.totalSize;
        }
    };

    $scope.removeLog = function(id) {
        logFactory.removeLog(id).then(function(success){
            $scope.search();
        }, function(err){
            console.error(err);
        });
    };
});
