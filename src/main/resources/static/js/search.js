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

app.controller("search", function($scope, $uibModal, opkpService, $http) {
   $scope.pageSizes = [10, 25, 100];

   $scope.search = function() {
      $scope.currentPage = 1;
      searchFood();
   }

   $scope.getSearches = function(val) {
      return opkpService.search("fir_food", val.split(/ +/), 0, 5).then(function(res) {
         return res.objects.map(function(food) {
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
         console.log( keywords.trim().replace(/\W/g, ','));

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
