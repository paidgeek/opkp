$.material.init();

var app = angular.module("app", ["ngPrettyJson"]);
var fitbit = new Fitbit();
google.charts.load('current', {
   'packages': ['line']
});

app.controller("home", function($scope, $http) {
   $http.get("/v1/me").success(function(data) {
      $scope.user = data;
   }).error(function() {
      $scope.user = "N/A";
      //window.location.href = "/";
   });

   $scope.logout = function() {
      $http.post('/logout', {}).success(function() {
         $scope.authenticated = false;
      }).error(function(data) {
         $scope.authenticated = false;
      });
   };
});

app.controller("content", function($scope, $http) {
   $scope.tabs = [{
      title: "User",
      url: "tabs/user.html"
   }, {
      title: "Heart Rate",
      url: "tabs/heart-rate.html"
   }, {
      title: "Food Logging",
      url: "tabs/food-logging.html"
   }];

   $scope.selectedTab = $scope.tabs[0];
   $scope.onClickTab = function(tab) {
      $scope.selectedTab = tab;
   }
   $scope.isActiveTab = function(tab) {
      return $scope.selectedTab.url == tab.url;
   }
   $scope.getTypeOf = function(obj) {
      return typeof obj;
   }
});

app.controller("user", function($scope) {
   $scope.responseData = null;
   $scope.getProfile = function() {
      fitbit.getProfile(function(data) {
         $scope.responseData = data["user"];
         tableCreator(data.user, "#resultTable");
      });
   }
});

app.controller("heart-rate", function($scope) {
   $scope.periods = ["1d", "7d", "30d", "1w", "1m"];

   $scope.periodChanged = function() {
      $scope.getHeartRate();
   };
   $scope.dateChanged = function() {
      $scope.getHeartRate();
   }

   $scope.responseData = null;
   $scope.error = null;
   $scope.getHeartRate = function() {
      fitbit.getHeartRate($scope.selectedDate, $scope.selectedPeriod, function(data) {
         $scope.responseData = data["activities-heart"];

         createChart($scope.responseData, "#heart-rate-chart");
      }, function(err) {
         $scope.error = JSON.stringify(err, null, 4);
      });
   }

   $("#heart-rate-chart").parent().resize(function() {
      createChart($scope.responseData, "#heart-rate-chart");
   });

   function createChart(data, chartId) {
      var labels = [];

      var dataTable = new google.visualization.DataTable();
      dataTable.addColumn("date", "Day");
      dataTable.addColumn("number", "Out of Range");
      dataTable.addColumn("number", "Fat Burn");
      dataTable.addColumn("number", "Cardio");
      dataTable.addColumn("number", "Peak");
      dataTable.addRows(data.length);

      for (var i = 0; i < data.length; i++) {
         var entry = data[i];
         var heartRateZones = entry["value"]["heartRateZones"];

         labels.push(entry["dateTime"]);

         for (var j = 0; j < heartRateZones.length; j++) {
            var dataset = heartRateZones[j];

            switch (dataset["name"]) {
               case "Out of Range":
                  dataTable.setCell(i, 0, dataset["caloriesOut"]);
                  break;
               case "Fat Burn":
                  dataTable.setCell(i, 0, dataset["caloriesOut"]);
                  break;
               case "Cardio":
                  dataTable.setCell(i, 0, dataset["caloriesOut"]);
                  break;
               case "Peak":
                  dataTable.setCell(i, 0, dataset["caloriesOut"]);
                  break;
            }
         }
      }

      var options = {
         chart: {
            title: 'Calories Out',
            subtitle: "subtitle"
         },
         width: "100%",
         height: 500
      };

      var chart = new google.charts.Line($(chartId)[0]);

      chart.draw(dataTable, options);
   }
});
