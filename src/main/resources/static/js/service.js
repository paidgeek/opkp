var OPKPService = {};
OPKPService.version = "v1";
OPKPService.host = "https://localhost:8080/" + OPKPService.version + "/";
OPKPService.timeout = 3000;

function get($http, $q, url, params) {
   var deferred = $q.defer();

   $http({
      method: "GET",
      url: url,
      headers: {
         "Accept": "application/json",
         "Content-Type": "application/json"
      },
      params: params,
      timeout: OPKPService.timeout
   }).success(function(data) {
      deferred.resolve(data);
   }).error(function(msg, code) {
      deferred.reject(msg);
      console.error(msg);
   });

   return deferred.promise;
}

function post($http, $q, url, data, params) {
   var deferred = $q.defer();

   $http({
      method: "POST",
      url: url,
      headers: {
         "Accept": "application/json",
         "Content-Type": "application/json"
      },
      params: params,
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
         search: function(keywords, skip, take) {
            if (typeof keywords === "object") {
               keywords = keywords.join(",");
            }

            var url = OPKPService.host + "batch";

            return post($http, $q, url, {
               commands: [{
                  name: "foods",
                  controller: "search",
                  arguments: "fir_food('" + keywords + "')",
                  params: {
                     skip: skip,
                     take: take
                  }
               }, {
                  name: "recipes",
                  controller: "search",
                  arguments: "fir_recipe('" + keywords + "')",
                  params: {
                     skip: skip,
                     take: take
                  }
               }]
            });
         },
         getFood: function(id) {
            var url = OPKPService.host + "batch";

            return post($http, $q, url, {
               commands: [{
                  name: "food",
                  controller: "path",
                  method: "GET",
                  arguments: "fir_food",
                  params: {
                     where: "ORIGFDCD:'" + id + "'"
                  }
               }, {
                  name: "components",
                  controller: "path",
                  arguments: "fir_food,fir_value,fir_component",
                  params: {
                     where: "fir_food.ORIGFDCD:'" + id + "'",
                     fields: [
                        "fir_component.ORIGCPNM",
                        "fir_value.SELVAL",
                        "fir_value.UNIT"
                     ]
                  },
                  dependencies: [{
                     command: "food",
                     condition: "status:200"
                  }]
               }]
            });
         },
         path: function(arguments, fields) {
            var url = OPKPService.host + "path/" + arguments.join(",");
            var params = {};

            if (fields && fields.length > 0) {
               params.fields = fields.join(",");
            }

            return get($http, $q, url, params);
         }
      }
   }
}
