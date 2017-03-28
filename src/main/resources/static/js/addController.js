app.controller('addController', function($scope, $rootScope, logFactory) {
    $rootScope.route = 'add';
    $scope.addLog = function(){
        var log = {
            level: $scope.level,
            dateTime: $scope.dateTime,
            source: $scope.source,
            eventId: $scope.eventId,
            taskCategory: $scope.taskCategory,
            info: $scope.info
        }; 
        saveLog(log);
    };

    var saveLog = function(log){
        logFactory.addLog(log).then(function(newLog){
            console.log(newLog);
        }, function(err){
            console.error(err);
        });
    }

    $scope.$watch('file', function(){
        $scope.uploadCSV($scope.file);
    });

    $scope.uploadCSV = function(file){
        if(file){
			var reader = new FileReader();

			reader.onload = function(e) {
				var csv = reader.result;
		  		parseCSV(csv);
		  		$scope.$apply();
			};

			reader.readAsText(file);
		}
    }

    var parseCSV = function(text) {
        //Remove the headers line
        var lines = text.split('\n');
        lines.splice(0,1);
        var csv = lines.join('\n').split(',');
        //Parse out the 6 columns
        for(var i = 0; i < csv.length; i += 6){
            var log = {
                level: csv[i],
                dateTime: csv[i+1],
                source: csv[i+2],
                eventId: parseInt(csv[i+3]),
                taskCategory: csv[i+4],
                info: csv[i+5]
            }; 
            saveLog(log);
        }
    }
});
