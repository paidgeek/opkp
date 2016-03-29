app.controller("graphs", function($scope, opkpService, dataGraph) {
   $scope.dataGraph = dataGraph;
   $scope.path = [];
   $scope.pathLength = 1;

   $scope.selectedColumns = [];

   $scope.getColumns = function(index) {
      return $scope.dataGraph.fields[$scope.path[index]].map(function(field) {
         return field.name;
      });
   }

   $scope.selectNode = function(index, node) {
      $scope.path[index] = node;
   }

   $scope.addNode = function() {
      $scope.pathLength++;
   }

   $scope.getSteps = function(start) {
      if (start < 0) {
         return Object.keys($scope.dataGraph.fields).sort();
      }

      var nodes = Object.keys($scope.dataGraph.fields).sort();

      if ($scope.pathLength > start && nodes) {
         var steps = [];
         var startNode = $scope.path[start];

         for (var i = 0; i < nodes.length; i++) {
            if ($scope.dataGraph.pathExists(startNode, nodes[i])) {
               steps.push(nodes[i]);
            }
         }

         for (var i = 0; i <= start; i++) {
            var idx = steps.indexOf($scope.path[i]);

            if (idx != -1) {
               steps.splice(idx, 1);
            }
         }

         return steps;
      }

      return [];
   }

   $scope.removeNode = function(i) {
      $scope.path.splice(i, $scope.pathLength);
      $scope.pathLength = $scope.path.length;
   }

   $scope.update = function() {
      if ($scope.path.length == 0 || !$scope.selectedColumns) {
         return;
      }

      var columns = [].concat.apply([], $scope.selectedColumns);

      opkpService.path($scope.path, columns).then(function(data) {
         $scope.responseData = data;
      });
   }
});
