var Fitbit = function(accessToken) {
   this.accessToken = accessToken;
   this.accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTc3MDk3NzMsInNjb3BlcyI6Indwcm8iLCJzdWIiOiI0REg5SEciLCJhdWQiOiIyMjdOUjQiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTc3MDYxNzN9.8tEhj_Xu1LUJXLvM5YjrWZ4Ejbibw-2mRCLoXNR9K20";
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
