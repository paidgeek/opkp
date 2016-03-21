app.controller("graphs", function($scope, opkpService, dataDefinition) {
   $scope.dd = dataDefinition;

   $scope.update = function() {
      opkpService.getData2D({
         table: $scope.selectedTableX,
         column: $scope.selectedColumnX
      }, {
         table: $scope.selectedTableY,
         column: $scope.selectedColumnY
      }, [50]).then(function(data) {
         $scope.data = data;
      });
   }
});
