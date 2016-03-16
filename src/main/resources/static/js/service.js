var OPKPService = {};
OPKPService.version = "v1";
OPKPService.host = "https://localhost:8080/" + OPKPService.version + "/";
OPKPService.timeout = 3000;

OPKPService.create = function() {
   return function($http, $log, $q) {
      return {
         searchFood: function(keywords, offset, count) {
            keywords = keywords.join(",");
            var url = OPKPService.host + "search/fir_food?columns=ORIGFDCD,ORIGFDNM,ENGFDNAM,SCINAM&keywords=" + keywords + "&limit=" + offset + "," + count;
            var deferred = $q.defer();

            $http.get(url, {
               headers: {
                  "Accept": "application/json",
                  "Content-Type": "application/json"
               },
               timeout: OPKPService.timeout
            }).success(function(data) {
               deferred.resolve(data);
            }).error(function(msg, code) {
               deferred.reject(msg);
               $log.error(msg, code);
            });

            return deferred.promise;
         }
      }
   }
}
