app.controller("graphs", function($scope, opkpService, dataDefinition, neighbours) {
   $scope.dataDefinition = dataDefinition;
   $scope.neighbours = neighbours;
   $scope.path = [];
   $scope.pathLength = 1;

   $scope.selectNode = function(index, node) {
      $scope.path[index] = node;
   }

   $scope.addNode = function() {
      $scope.pathLength++;
   }

   $scope.getSteps = function(start) {
      if (start < 0) {
         return Object.keys(dataDefinition);
      }

      var neighbours = $scope.neighbours[$scope.path[start]];

      if ($scope.pathLength > start && neighbours) {
         var steps = neighbours.slice();

         for (var i = 0; i < start; i++) {
            var n = $scope.path[i];
            var idx = steps.indexOf(n);

            if (idx != -1) {
               steps.splice(idx, 1);
            }
         }

         return steps;
      }

      return [];
   }

   $scope.removeNode = function() {
      $scope.path.splice($scope.pathLength - 1, 1);
      $scope.pathLength--;
   }

   $scope.update = function() {}
});
