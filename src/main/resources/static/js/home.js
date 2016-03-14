$.material.init();

var app = angular.module("app", ["ngPrettyJson"]);
var fitbit = new Fitbit();

app.controller("home", function($scope, $http) {
   $http.get("/v1/me").success(function(data) {
      $scope.user = data;
   }).error(function() {
      $scope.user = "N/A";
      //window.location.href = "/";
   });

   $scope.logout = function() {
      $http.post('/logout', {}).success(function() {
         $scope.authenticated = false;
      }).error(function(data) {
         $scope.authenticated = false;
      });
   };
});

app.controller("content", function($scope, $http) {
   $scope.tabs = [{
      title: "User",
      url: "tabs/user.html"
   }, {
      title: "Heart Rate",
      url: "tabs/heart-rate.html"
   }, {
      title: "Food Logging",
      url: "tabs/food-logging.html"
   }];

   $scope.selectedTab = $scope.tabs[0];
   $scope.onClickTab = function(tab) {
      $scope.selectedTab = tab;
   }
   $scope.isActiveTab = function(tab) {
      return $scope.selectedTab.url == tab.url;
   }
   $scope.getTypeOf = function(obj) {
      return typeof obj;
   }
});

app.controller("user", function($scope) {
   $scope.responseData = null;
   $scope.getProfile = function() {
      fitbit.getProfile(function(data) {
         $scope.responseData = data.user;
         tableCreator(data.user, "#resultTable");
      });
   }
});

app.controller("heart-rate", function($scope) {
   $scope.responseData = null;
   $scope.getHeartRate = function() {
      fitbit.getHeartRate(function(data) {
         console.log(data);
      });
   }
});
