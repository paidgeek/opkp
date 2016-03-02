var OPKP = {};

OPKP.host = "http://localhost:8080/";
OPKP.timeout = 3000;

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

OPKP.findFoods = function (keywords, offset, count, success, error) {
    keywords = keywords.join(",");

    var url = OPKP.host + "food/search?keywords=" + keywords + "&offset=" + offset + "&count=" + count;

    $.ajax({
        type: "GET",
        url: url,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
}
