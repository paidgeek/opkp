var OPKPService = {};
OPKPService.version = "v1";
OPKPService.host = "https://localhost:8080/" + OPKPService.version + "/";
OPKPService.timeout = 3000;

function get($http, $q, url) {
   var deferred = $q.defer();

   $http({
      method: "GET",
      url: url,
      headers: {
         "Accept": "application/json",
         "Content-Type": "application/json"
      },
      timeout: OPKPService.timeout
   }).success(function(data) {
      deferred.resolve(data);
   }).error(function(msg, code) {
      deferred.reject(msg);
      console.error(msg);
   });

   return deferred.promise;
}

function post($http, $q, url, data) {
   var deferred = $q.defer();

   $http({
      method: "POST",
      url: url,
      headers: {
         "Accept": "application/json",
         "Content-Type": "application/json"
      },
      timeout: OPKPService.timeout,
      data: data
   }).success(function(data) {
      deferred.resolve(data);
   }).error(function(msg, code) {
      deferred.reject(msg);
      console.error(msg);
   });

   return deferred.promise;
}

OPKPService.create = function() {
   return function($http, $q) {
      return {
         searchFood: function(keywords, offset, count) {
            keywords = keywords.join(",");
            var url = OPKPService.host + "search/fir_food?columns=ORIGFDCD,ORIGFDNM,ENGFDNAM,SCINAM&keywords=" + keywords + "&limit=" + offset + "," + count;

            return get($http, $q, url);
         },
         getFood(id) {
            var url = OPKPService.host + "batch";

            return post($http, $q, url, {
               commands: [{
                  name: "food",
                  controller: "crud",
                  method: "GET",
                  model: "fir_food",
                  params: {
                     q: "ORIGFDCD:'" + id + "'"
                  }
               }, {
                  name: "components",
                  controller: "graph",
                  model: "fir_food,fir_value,fir_component",
                  params: {
                     q: "ORIGFDCD:'" + id + "'",
                     columns: ["ORIGCPNM", "SELVAL"]
                  },
                  dependencies: ["food"]
               }]
            });
         }
      }
   }
}
