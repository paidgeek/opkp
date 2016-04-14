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
				var url = OPKPService.host + "graph/fir_recipe/'" + id + "'";

				return get($http, $q, url, {
					fields: "fir_ingredients(),fir_food().fields(fc_foodsubgroup().fields(fc_foodgroup()))"
				});
			},
			getFood: function (id) {
				var url = OPKPService.host + "graph/fir_food/'" + id + "'";

				return get($http, $q, url, {
					fields: "fc_foodsubgroup().fields(fc_foodgroup()),fir_value().fields(SELVAL,UNIT,fir_component().fields(ORIGCPNM))"
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
