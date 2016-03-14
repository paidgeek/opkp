var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
   this.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTc5NTA2MDIsInNjb3BlcyI6Indwcm8iLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTc5NDcwMDJ9.4G4LCvP2euOAk2Xu8ikPQA1Q3GcWgqS6xqbmsQ0scQA";
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

Fitbit.prototype.getHeartRate = function(success, error) {
   var url = "https://api.fitbit.com/1/user/[user-id]/activities/heart/date/[date]/[period].json";

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
