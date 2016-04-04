app.controller('food-modal', function($scope, $uibModalInstance, opkpService, food) {
   opkpService.getFood(food["ORIGFDCD"]).then(function(data) {
      console.log(data);

      $scope.data = {
         components: data.components.total == 0 ? null : data.components.objects,
         food: data.food.objects[0]
      };
   });

   $scope.ok = function() {
      $uibModalInstance.close();
   };

   $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
   };
});

app.controller('recipe-modal', function($scope, $uibModalInstance, opkpService, recipe) {
   opkpService.getRecipe(recipe["RECID"]).then(function(data) {
      $scope.data = {
         ingredients: data.ingredients.objects[0],
         recipe: data.recipe.objects[0]
      };
   });

   $scope.ok = function() {
      $uibModalInstance.close();
   };

   $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
   };
});

app.controller("search", function($scope, $uibModal, opkpService, $http) {
   $scope.pageSize = 20;

   $scope.search = function() {
      $scope.currentPage = 1;
      searchFood();
   }

   $scope.getSearches = function(val) {
      return opkpService.search(val.split(/ +/), 0, 5).then(function(res) {
         return res.foods.objects.map(function(food) {
            return food.ORIGFDNM;
         });
      });
   }

   $scope.onPageChanged = function(page) {
      $scope.currentPage = page;
      window.scrollTo(0, 0);

      searchFood();
   }

   function searchFood(success, error) {
      var keywords = $scope.keywords;

      if (keywords && keywords.trim()) {
         var promise = opkpService.search(keywords, ($scope.currentPage - 1) * $scope.pageSize, $scope.pageSize);
         var startTime = new Date().getTime();
         $scope.searchPromise = promise;

         promise.then(function(data) {
            $scope.responseTime = (new Date().getTime() - startTime) / 1000.0;
            $scope.responseData = data;

            if (success) {
               success();
            }
         });
      } else {
         $scope.responseData = null;
         if (error) {
            error();
         }
      }
   }

   $scope.onEditFoodClick = function(row) {
      var modal = $uibModal.open({
         templateUrl: "templates/food-modal.html",
         controller: "food-modal",
         size: "lg",
         resolve: {
            food: function() {
               return row;
            }
         }
      });
   };

   $scope.onEditRecipeClick = function(row) {
      var modal = $uibModal.open({
         templateUrl: "templates/recipe-modal.html",
         controller: "recipe-modal",
         size: "lg",
         resolve: {
            recipe: function() {
               return row;
            }
         }
      });
   }
});
