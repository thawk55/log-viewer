app.controller('addController', function($scope, $rootScope, logFactory) {
    $rootScope.route = 'add';
    $scope.uploading = false;

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
            $scope.uploaded = true;
            $scope.uploading = false;
            $scope.file = null;
        }, function(err){
            console.error(err);
        });
    }

    $scope.$watch('file', function(){
        $scope.uploadCSV($scope.file);
    });

    $scope.uploadCSV = function(file){
        if(file){
            $scope.uploaded = false;
            $scope.uploading = true;
			var reader = new FileReader();

			reader.onload = function(e) {
				var csv = reader.result;
		  		parseCSV(csv);
		  		$scope.$apply();
			};

			reader.readAsText(file);
		}
    }

    var trimToComma = function(csv) {
        var index = csv.indexOf(',');
        if(index == -1){
            return {
                trimmed: csv,
                remainder: ''
            };
        }
        return {
            trimmed: csv.substr(0, index),
            remainder: csv.substr(index + 1)
        };
    };

    var getInfo = function(csv) {
        if (csv[0] == '"') {
            var nextQuotePosition;
            for (var i = 1; i < csv.length; i++) {
                if (csv[i] == '"') {
                    if (csv[i+1] != '"') {
                        nextQuotePosition = i;
                        break;
                    } else {
                        i++; //Skip the next quote.
                    }
                }
            }
            
            return {
                info: csv.substr(0, nextQuotePosition),
                remainder: csv.substr(nextQuotePosition + 2) //Get newLine
            }
        } else {
            var lines = csv.split(/\r\n|\n|\r/);
            info = lines[0];
            lines.splice(0,1);
            return {
                info: info,
                remainder: lines.join('\n')
            }
        }
    };

    var parseCSV = function(text) {
        //Info column is always in Double Quotes.

        //Remove the headers line
        var lines = text.split('\n');
        lines.splice(0,1);
        var csv = lines.join('\n');
        //Parse out the 6 columns
        while(csv.length > 0){
            var columns = [];
            for(var count = 0; count < 5; count++){
                var result = trimToComma(csv);
                columns.push(result.trimmed);
                csv = result.remainder;
            }
            var level = columns[0].replace(/(\r\n|\n|\r)/gm, ""); // Remove any extra newlines.
            var dateTime = columns[1];
            var source = columns[2];
            var eventId = parseInt(columns[3]);
            var taskCategory = columns[4];
            var infoResults = getInfo(csv);
            var info = infoResults.info;
            csv = infoResults.remainder;
            var log = {
                level: level, 
                dateTime: dateTime, 
                source: source, 
                eventId: eventId, 
                taskCategory: taskCategory, 
                info: info, 
            }; 
            saveLog(log);
        }
    }
});
