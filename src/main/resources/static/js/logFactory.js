app.factory('logFactory', function($http, $q) {
  let api = 'http://localhost:8080/';
  return {
    getLogs: function(search, limit, skip){
      var defer = $q.defer();
      var payload = {
        params: {
          search: search,
          limit: limit,
          skip: skip,
        }
      };
      $http.get(api + 'logs', payload).then(function(response){
        defer.resolve(response.data);
      }, function(response){
        defer.reject(response.data);
      });
      return defer.promise;
    },
    getLog: function(logId){
      var defer = $q.defer();
      $http.get(api + 'logs/' + logId).then(function(response){
        defer.resolve(response.data);
      }, function(response){
        defer.reject(response.data);
      });
      return defer.promise;
    },
    addLog: function(log){
      var defer = $q.defer();
      $http.post(api + 'logs', log).then(function(response){
        defer.resolve(response.data);
      }, function(response){
        defer.reject(response.data);
      });
      return defer.promise;
    },
    removeLog: function(logId){
      var defer = $q.defer();
      $http.delete(api + 'logs/' + logId).then(function(response){
        defer.resolve(response);
      }, function(response){
        defer.reject(response.data);
      });
      return defer.promise;
    }
  }; 
});
