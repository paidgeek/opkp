app.controller("graphs", function($scope, opkpService, dataGraph) {
   $scope.dataGraph = dataGraph;
   $scope.path = [];
   $scope.pathLength = 1;

   $scope.selectedColumns = [];

   $scope.getColumns = function(index) {
      return Object.keys($scope.dataGraph.definitions[$scope.path[index]]);
   }

   $scope.selectNode = function(index, node) {
      $scope.path[index] = node;
   }

   $scope.addNode = function() {
      $scope.pathLength++;
   }

   $scope.getSteps = function(start) {
      if (start < 0) {
         return Object.keys($scope.dataGraph.definitions);
      }

      var nodes = Object.keys($scope.dataGraph.definitions);

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
      if (!$scope.selectedColumns) {
         return;
      }

      var start = $scope.path[0];
      var goals = $scope.path.slice(1);
      var columns = [].concat.apply([], $scope.selectedColumns);

      if (goals.length == 0) {
         opkpService.graph(start, columns).then(function(data) {
            $scope.responseData = data;
         });
      } else {
         opkpService.path(start, goals, columns).then(function(data) {
            $scope.responseData = data;
         });
      }
   }
});
