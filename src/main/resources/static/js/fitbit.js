var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
   this.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTc3MDU1MTQsInNjb3BlcyI6Indwcm8iLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTc3MDE5MTR9.vp7FVGvcABAk0MNL4N01m_UD_bbJlH4aK-08bQh5I7M";
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
