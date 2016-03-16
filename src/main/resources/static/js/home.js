$.material.init();
google.charts.load('current', {
   'packages': ['line']
});
var app = angular.module("app", ["ngCookies", "jsonFormatter"]);
var fitbit = null;

app.controller("home", ["$scope", "$http", "$cookies", function($scope, $http, $cookies) {
   var user = $cookies.getObject("user");

   if (!user) {
      window.location.href = "/";
      return;
   }

   fitbit = new Fitbit(user.accessToken);

   $scope.logout = function() {
      logout($http, $cookies);
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

app.controller("user", function($scope, $http, $cookies) {
   $scope.responseData = null;
   $scope.getProfile = function() {
      fitbit.getProfile(function(data) {
         $scope.responseData = data["user"];
      }, function(err) {
         if (err.status == 401) {
            logout($http, $cookies);
         } else {
            console.error(err.responseText);
         }
      });
   }
});

app.controller("heart-rate", function($scope, $http, $cookies) {
   $scope.periods = ["7d", "30d", "1w", "1m"];

   $scope.periodChanged = function() {
      $scope.getHeartRate();
   };
   $scope.dateChanged = function() {
      $scope.getHeartRate();
   };

   $scope.getHeartRate = function() {
      $scope.error = null;

      fitbit.getHeartRate($scope.selectedDate, $scope.selectedPeriod, function(data) {
         $scope.responseData = data;
         var columns = [{
            name: "Day",
            type: "date"
         }, {
            name: "Out of Range",
            type: "number"
         }, {
            name: "Fat Burn",
            type: "number"
         }, {
            name: "Cardio",
            type: "number"
         }, {
            name: "Peak",
            type: "number"
         }];
         createChart($("#heart-rate-chart")[0], extractHeartRateRows(data["activities-heart"], "caloriesOut"), columns, "Calories Out");
         createChart($("#minutes-chart")[0], extractHeartRateRows(data["activities-heart"], "minutes"), columns, "Time", "in minutes");
      }, function(err) {
         if (err.status == 401) {
            logout($http, $cookies);
         } else {
            console.error(err.responseText);
         }
      });
   }
});

app.controller("sleep", function($scope, $http, $cookies) {
   $scope.periods = ["7d", "30d", "1w", "1m"];
   $scope.sleepResources = [{
      name: "startTime",
      title: "Start Time"
   }, {
      name: "timeInBed",
      title: "Time in bed"
   }, {
      name: "minutesAsleep",
      title: "Minutes asleep"
   }, {
      name: "awakeningsCount",
      title: "Awakenings Count"
   }, {
      name: "minutesAwake",
      title: "Minutes Awake"
   }, {
      name: "minutesToFallAsleep",
      title: "Minutes to fall asleep"
   }, {
      name: "minutesAfterWakeup",
      title: "Minutes after wakeup"
   }, {
      name: "efficiency",
      title: "Efficiency"
   }];
   $scope.periodChanged = $scope.dateChanged = $scope.sleepResourceChanged = function() {
      if ($scope.selectedPeriod && $scope.selectedSleepResource && $scope.selectedDate) {
         $scope.getSleep();
      }
   };

   $scope.getSleep = function() {
      fitbit.getSleep($scope.selectedSleepResource.name, $scope.selectedDate, $scope.selectedPeriod, function(data) {
         $scope.responseData = data;
         var columns = [{
            name: "Day",
            type: "date"
         }, {
            name: "Value",
            type: "number"
         }];
         var rows = [];
         for (var i = 0; i < data["sleep-" + $scope.selectedSleepResource.name].length; i++) {
            var row = data["sleep-" + $scope.selectedSleepResource.name][i];

            rows.push([new Date(row["dateTime"]), parseInt(row["value"])]);
         }

         createChart($("#sleep-chart")[0], rows, columns, $scope.selectedSleepResource.title);
      }, function(err) {
         if (err.status == 401) {
            logout($http, $cookies);
         } else {
            console.error(err.responseText);
         }
      });
   }
});

function createChart(chartDiv, rows, columns, title, subtitle) {
   var dataTable = new google.visualization.DataTable();

   for (var i = 0; i < columns.length; i++) {
      var col = columns[i];

      dataTable.addColumn(col.type, col.name);
   }

   dataTable.addRows(rows);

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

function logout($http, $cookies) {
   $http.post('/logout', {}).success(function() {
      $cookies.remove("user");
      $cookies.remove("JSESSIONID");
      window.location.href = "/";
   }).error(function(data) {
      $cookies.remove("user");
      $cookies.remove("JSESSIONID");
      window.location.href = "/";
   });
}

function extractHeartRateRows(data, columnName) {
   var rows = [];

   for (var i = 0; i < data.length; i++) {
      var entry = data[i];
      var heartRateZones = entry["value"]["heartRateZones"];

      var row = [null, 0, 0, 0, 0];
      row[0] = new Date(entry["dateTime"]);

      for (var j = 0; j < heartRateZones.length; j++) {
         var dataset = heartRateZones[j];
         var k = {
            "Out of Range": 1,
            "Fat Burn": 2,
            "Cardio": 3,
            "Peak": 4,
         }[dataset["name"]];

         if (dataset[columnName]) {
            row[k] = dataset[columnName];
         }
      }

      rows.push(row);
   }

   return rows;
}
