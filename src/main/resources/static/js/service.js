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
						path: "fir_food/" + keywords,
						params: {
							skip: skip,
							take: take
						}
					}, {
						name: "recipes",
						controller: "search",
						path: "fir_recipe/" + keywords,
						params: {
							skip: skip,
							take: take
						}
					}]
				});
			},
			getRecipe: function(id) {
				var url = OPKPService.host + "batch";

				return post($http, $q, url, {
					commands: [{
						name: "recipe",
						controller: "path",
						method: "GET",
						arguments: "fir_recipe",
						params: {
							where: "RECID:'" + id + "'",
							fields: ["RECID", "INGNAM", "INGAMOUNT", "INGUNIT", "HMID", "RANK", "EDIBLE", "WHOLE_WHEAT", "SOAK", "REMARKS", "ORIGCPNM", "ENGFDNAM", "SCINAM"]
						}
					}, {
						name: "ingredients",
						controller: "path",
						arguments: "fir_ingredients,fir_food",
						params: {
							where: "fir_ingredients.RECID:'" + id + "'"
						},
						dependencies: [{
							command: "recipe",
							condition: "status:200"
						}]
					}]
				});
			},
			getFood: function(id) {
				var url = OPKPService.host + "batch";

				return post($http, $q, url, {
					commands: [{
						name: "food",
						controller: "graph",
						path: "fir_food/'" + id + "'",
						params: {
							fields: "ORIGFDCD,ENGFDNAM,SCINAM"
						}
					}, {
						name: "components",
						controller: "graph",
						path: "fir_food/'" + id + "'",
						params: {
							fields: "fir_value().fields(SELVAL,fir_component().fields(ORIGCPNM))"
						},
						dependencies: [{
							command: "food",
							condition: "status:200"
						}]
					}]
				});
			},
			graph: function(arguments, fields) {
				var url = OPKPService.host + "graph/" + arguments.join(",");
				var params = {};

				if (fields && fields.length > 0) {
					params.fields = fields.join(",");
				}

				return get($http, $q, url, params);
			}
		}
	}
}
