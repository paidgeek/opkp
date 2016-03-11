$.material.init();

var app = angular.module("app", []);

app.controller("login", function($scope, $http) {
   $http.get("/v1/me").success(function(data) {
      console.log(data);
      //window.location.href = "/home.html";
   }).error(function() {
      $scope.user = "N/A";
   });
});
