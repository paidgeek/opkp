var OPKP = {};

OPKP.host = "http://localhost:8080/";

OPKP.select = function (statement, success, error) {
    $.ajax({
        type: "POST",
        url: OPKP.host + "select",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(statement),
        timeout: 3000,
        success: success,
        error: error
    });
};
