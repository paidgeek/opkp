var OPKP = {};

OPKP.host = "http://localhost:8080/api/";

OPKP.getAllFoods = function (callback) {
    $.ajax({
        url: OPKP.host + "food"
    }).then(function (data) {
        callback(data);
    });
};
