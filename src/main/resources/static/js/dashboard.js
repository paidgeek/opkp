var app = angular.module("app", ["cgBusy", "ui.bootstrap"]);

app.factory("opkpService", OPKPService.create());

app.controller("nav", function($scope) {
   $scope.tabs = [{
      title: "Foods",
      href: "tabs/foods.html"
   }, {
      title: "Recipes",
      href: "tabs/recipes.html"
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
});

app.controller('food-modal', function($scope, $uibModalInstance, opkpService, food) {
   $scope.food = food;

   $scope.ok = function() {
      $uibModalInstance.close();
   };

   $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
   };
});

app.controller("foods", function($scope, $uibModal, opkpService) {
   $scope.pageSizes = [10, 25, 100];

   $scope.search = function() {
      $scope.currentPage = 1;
      searchFood();
   }
   $scope.onPageChanged = function(page) {
      $scope.currentPage = page;
      window.scrollTo(0, 0);

      searchFood();
   }

   function searchFood(success, error) {
      var keywords = $scope.keywords;

      if (keywords && keywords.trim()) {
         keywords = keywords.trim().replace(/\W/g, '').split(/ +/);
         var promise = opkpService.searchFood(keywords, ($scope.currentPage - 1) * $scope.pageSize, $scope.pageSize);
         $scope.searchPromise = promise;

         promise.then(function(data) {
            $scope.data = data;

            if (success) {
               success();
            }
         });
      } else {
         $scope.data = null;
         if (error) {
            error();
         }
      }
   }

   $scope.onEditClick = function(row) {
      var modal = $uibModal.open({
         templateUrl: "food-modal.html",
         controller: "food-modal",
         size: "lg",
         resolve: {
            food: function() {
               return row;
            }
         }
      });
   };
});
