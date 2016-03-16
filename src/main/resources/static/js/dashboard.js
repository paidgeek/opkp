$.material.init();
var app = angular.module("app", ["cgBusy"]);

app.factory("opkpService", OPKPService.create());

app.controller("nav", ["$scope", function($scope) {
   $scope.tabs = [{
      title: "Explore",
      href: "tabs/explore.html"
   }, {
      title: "Graphs",
      href: "tabs/graphs.html"
   }];
   $scope.selectedTab = $scope.tabs[0];
   $scope.onClickTab = function(tab) {
      $scope.selectedTab = tab;
   }
   $scope.isActiveTab = function(tab) {
      return $scope.selectedTab.href == tab.href;
   }
   $scope.getTypeOf = function(obj) {
      return typeof obj;
   }
}]);

app.controller("explore", ["$scope", "opkpService", function($scope, opkpService) {
   $scope.search = function() {
      var keywords = $scope.keywords.trim().split(/ +/);

      if (keywords) {
         var promise = opkpService.searchFood(keywords, 0, 10);
         $scope.searchPromise = promise;
         promise.then(function(data) {
            $scope.results = data.result;
         });
      } else {
         $scope.results = null;
      }
   }
}]);
