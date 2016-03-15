$.material.init();
google.charts.load('current', {
   'packages': ['line']
});
var app = angular.module("app", ["ngPrettyJson", "ngCookies"]);
var fitbit = new Fitbit("eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTgwNDUxNTEsInNjb3BlcyI6Indsb2Mgd3BybyB3bnV0IHdzbGUgd3NldCB3aHIgd3dlaSB3YWN0IHdzb2MiLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTgwNDE1NTF9.YASioakmUBBn0e0Pad_qxXrWGofLvXlOo5pzTof4gYo");

app.controller("home", ["$scope", "$http", function($scope, $http) {
   $scope.logout = function() {
      $http.post('/logout', {}).success(function() {
         window.location.href = "/";
      }).error(function(data) {
         window.location.href = "/";
      });
   };
}]);

app.controller("content", function($scope, $http) {
   $scope.tabs = [{
      title: "User",
      url: "tabs/user.html"
   }, {
      title: "Heart Rate",
      url: "tabs/heart-rate.html"
   }, {
      title: "Sleep",
      url: "tabs/sleep.html"
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
      }, function(err) {
         if (err.status == 401) {
            window.location.href = "/";
         } else {
            console.error(err.responseText);
         }
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
         if (err.status == 401) {
            window.location.href = "/";
         } else {
            console.error(err.responseText);
         }
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

app.controller("sleep", function($scope) {
   $scope.periods = ["1d", "7d", "30d", "1w", "1m"];
   $scope.periodChanged = function() {
      $scope.getSleep();
   };
   $scope.dateChanged = function() {
      $scope.getSleep();
   };

   $scope.getSleep = function() {
      fitbit.getSleep("startTime", $scope.selectedDate, $scope.selectedPeriod, function(data) {
         console.log(data);
      }, function(err) {
         if (err.status == 401) {
            window.location.href = "/";
         } else {
            console.error(err.responseText);
         }
      });
   }
});
