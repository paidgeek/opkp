var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
};

Fitbit.prototype.getProfile = function(success, error) {
   var url = "https://api.fitbit.com/1/user/-/profile.json";

   $.ajax({
      url: url,
      type: "GET",
      headers: {
         "Authorization": "Bearer " + this.accessToken
      },
      success: success,
      error: error
   });
};

Fitbit.prototype.getHeartRate = function(date, period, success, error) {
   var url = "https://api.fitbit.com/1/user/-/activities/heart/date/" + formatDate(date) + "/" + period + ".json";

   $.ajax({
      url: url,
      type: "GET",
      headers: {
         "Authorization": "Bearer " + this.accessToken
      },
      success: success,
      error: error
   });
}

function formatDate(date) {
   var d = new Date(date),
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

   if (month.length < 2) month = '0' + month;
   if (day.length < 2) day = '0' + day;

   return [year, month, day].join('-');
}
