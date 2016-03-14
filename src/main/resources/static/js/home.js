$.material.init();

var app = angular.module("app", ["ngPrettyJson"]);
var fitbit = new Fitbit();

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

         createChart($scope.responseData, $("#heart-rate-chart"));
      }, function(err) {
         $scope.error = JSON.stringify(err, null, 4);
      });
   }

   $("#heart-rate-chart").parent().resize(function() {
      createChart($scope.responseData, this);
   });

   function createChart(data, canvas) {
      var labels = [];
      var caloriesOut = [];

      for (var i = 0; i < data.length; i++) {
         var entry = data[i];
         var heartRateZones = entry["value"]["heartRateZones"];

         labels.push(entry["dateTime"]);

         for (var j = 0; j < heartRateZones.length; j++) {
            var dataset = heartRateZones[j];

            var min = dataset["min"];
            var max = dataset["max"];
            var name = dataset["name"];

            if (dataset["caloriesOut"]) {
               caloriesOut.push(dataset["caloriesOut"]);
            } else {
               caloriesOut.push(Math.random() * (max - min) + min);
            }
         }
      }

      var chartData = {
         labels: labels,
         datasets: [{
            label: "Calories Out",
            fillColor: "rgba(220,220,220,0.5)",
            strokeColor: "rgba(220,220,220,0.8)",
            highlightFill: "rgba(220,220,220,0.75)",
            highlightStroke: "rgba(220,220,220,1)",
            data: caloriesOut
         }]
      };

      canvas.attr("width", canvas.parent().width());
      canvas.attr("height", canvas.parent().height());

      var ctx = canvas[0].getContext("2d");
      new Chart(ctx).Bar(chartData, {});
   }
});
