$.material.init();

var app = angular.module("app", ["ngCookies"]);

app.controller("login", ["$scope", "$http", "$cookies", function($scope, $http, $cookies) {
   $http.get("/v1/me").success(function(data) {
      $cookies.put("user", data);
      window.location.href = "/home.html";
   }).error(function() {
      $scope.user = "N/A";
   });
}]);
