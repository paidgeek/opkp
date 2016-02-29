var OPKP = {};

OPKP.host = "http://localhost:8080/";
OPKP.timeout = 3000;

OPKP.select = function (statement, success, error) {
    $.ajax({
        type: "POST",
        url: OPKP.host + "select",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(statement),
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
};

OPKP.getFood = function (foodId, success, error) {
    $.ajax({
        type: "GET",
        url: OPKP.host + "food/" + foodId,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
}
