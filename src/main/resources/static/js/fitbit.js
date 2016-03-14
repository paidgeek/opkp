var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
   this.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTc5Njc5OTgsInNjb3BlcyI6Indsb2Mgd3BybyB3bnV0IHdzZXQgd3NsZSB3aHIgd3dlaSB3YWN0IHdzb2MiLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTc5NjQzOTh9.o5pGgMtwDkce97Y3CwsP7-MC7nlw1-edgSg5NiJylIw";
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
