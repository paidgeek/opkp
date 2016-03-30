app.controller('food-modal', function($scope, $uibModalInstance, opkpService, food) {
   opkpService.getFood(food["ORIGFDCD"]).then(function(data) {
      $scope.data = {
         components: data.components.meta.total == 0 ? null : data.components.result,
         food: data.food.result[0]
      };
   });

   $scope.ok = function() {
      $uibModalInstance.close();
   };

   $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
   };
});

app.controller("search", function($scope, $uibModal, opkpService) {
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
         var promise = opkpService.search("fir_food", keywords, ($scope.currentPage - 1) * $scope.pageSize, $scope.pageSize);
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
