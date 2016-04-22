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
	}).success(function (data) {
		deferred.resolve(data);
	}).error(function (msg, code) {
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
	}).success(function (data) {
		deferred.resolve(data);
	}).error(function (msg, code) {
		deferred.reject(msg);
		console.error(msg);
	});

	return deferred.promise;
}

OPKPService.create = function () {
	return function ($http, $q) {
		return {
			search: function (node, keywords, skip, take) {
				if (typeof keywords === "object") {
					keywords = keywords.join(",");
				}

				var url = OPKPService.host + "search/" + node + "/" + keywords;

				return get($http, $q, url, {
					skip: skip,
					take: take
				});
			},
			getRecipe: function (id) {
				var url = OPKPService.host + "batch";

				return post($http, $q, url, {
					commands: [{
						name: "recipe",
						controller: "graph",
						path: "fir_recipe/'" + id + "'",
						params: {
							fields: "fir_food().fields(fc_foodsubgroup().fields(fc_foodgroup()))"
						}
					}, {
							name: "ingredients",
							controller: "graph",
							path: "fir_ingredients",
							params: {
								fields: "fir_food()",
								where: "RECID:'" + id + "'"
							}
						}]
				});
			},
			getFood: function (id) {
				var url = OPKPService.host + "batch";

				return post($http, $q, url, {
					commands: [{
						name: "food",
						controller: "graph",
						path: "fir_food/'" + id + "'",
						params: {
							fields: "fc_foodsubgroup().fields(fc_foodgroup()),fir_value().fields(SELVAL,UNIT,fir_component().fields(ORIGCPNM))"
						}
					}, {
							name: "ingredients",
							controller: "graph",
							path: "fir_ingredients",
							params: {
								fields: "fir_recipe().fields(fir_food())",
								where: "FOODID:'" + id + "'"
							}
						}]
				});

			},
			graph: function (arguments, fields) {
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
