$.material.init();
google.charts.load('current', {
   'packages': ['line']
});
var app = angular.module("app", ["ngPrettyJson"]);
var fitbit = new Fitbit("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTgwMzE3MzQsInNjb3BlcyI6Indwcm8gd2xvYyB3bnV0IHdzbGUgd3NldCB3aHIgd3dlaSB3YWN0IHdzb2MiLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTgwMjgxMzR9.dsAZDDRTup3mc1jYvT1np6oVBWuVQcEOQ5Pm9q9oZmo");

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
   };

   $scope.getHeartRate = function() {
      $scope.error = null;

      fitbit.getHeartRate($scope.selectedDate, $scope.selectedPeriod, function(data) {
         createChart(data["activities-heart"], $("#heart-rate-chart")[0], "caloriesOut", "Calories Out");
         createChart(data["activities-heart"], $("#minutes-chart")[0], "minutes", "Time", "in minutes");
         $scope.responseData = data;
      }, function(err) {
         $scope.error = JSON.stringify(err, null, 4);
      });
   }

   function createChart(data, chartDiv, columnName, title, subtitle) {
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

         dataTable.setCell(i, 0, new Date(entry["dateTime"]));

         for (var j = 0; j < heartRateZones.length; j++) {
            var dataset = heartRateZones[j];
            var k = {
               "Out of Range": 1,
               "Fat Burn": 2,
               "Cardio": 3,
               "Peak": 4,
            }[dataset["name"]];

            if (dataset[columnName]) {
               dataTable.setCell(i, k, dataset[columnName]);
            } else {
               dataTable.setCell(i, k, Math.random() * 300 + 600);
            }
         }
      }

      var options = {
         chart: {
            title: title,
            subtitle: subtitle
         },
         width: "100%",
         height: 500
      };

      var chart = new google.charts.Line(chartDiv);

      chart.draw(dataTable, options);
   }
});
