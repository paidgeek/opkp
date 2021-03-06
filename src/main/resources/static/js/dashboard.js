var app = angular.module("app", ["cgBusy", "ui.bootstrap", "am.multiselect", "xeditable"]);

app.run(function(editableOptions) {
   editableOptions.theme = "bs3";
});

app.factory("opkpService", OPKPService.create());

app.controller("nav", function($scope) {
   $scope.tabs = [{
      title: "Search",
      href: "tabs/search.html"
   }, {
      title: "Explore",
      href: "tabs/explore.html"
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
   $scope.range = function(n) {
      var arr = new Array(n);

      for (var i = 0; i < n; i++) {
         arr[i] = i;
      }

      return arr;
   }
});
