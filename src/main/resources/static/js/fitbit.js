var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
   this.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTc5NjQzNTIsInNjb3BlcyI6Indsb2Mgd3BybyB3bnV0IHdzZXQgd3NsZSB3d2VpIHdociB3YWN0IHdzb2MiLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTc5NjA3NTJ9.bivDSSI6YAI4uwZZWy3KxdYbz13N0TioYrKllwIyr5w";
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
