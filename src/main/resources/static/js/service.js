var OPKP = {};

OPKP.version = "v1";
OPKP.host = "http://localhost:8080/" + OPKP.version + "/";
OPKP.timeout = 3000;

OPKP.batch = function (commands, success, error) {
    $.ajax({
        type: "POST",
        url: OPKP.host + "batch",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify({
            commands: commands
        }),
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
};

OPKP.create = function (model, body, success, error) {
    $.ajax({
        type: "POST",
        url: OPKP.host + "crud/" + model,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(body),
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
};

OPKP.read = function (model, params, success, error) {
    var url = OPKP.host + "crud/" + model + "?";

    if (params) {
        if (params["columns"]) {
            url += "&columns=" + params.columns.join(",")
        }

        if (params["sort"]) {
            url += "&sort=" + params.sort.join(",");
        }

        if (params["query"]) {
            url += "&q=" + params.query;
        }
    }

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
};

OPKP.update = function (model, query, body, success, error) {
    $.ajax({
        type: "GET",
        url: OPKP.host + "crud/" + model + "?q=" + query,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        data: JSON.stringify(body),
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
};

OPKP.delete = function (model, query, success, error) {
    $.ajax({
        type: "DELETE",
        url: OPKP.host + "crud/" + model + "?q=" + query,
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        timeout: OPKP.timeout,
        success: success,
        error: error
    });
};

OPKP.getFood = function (foodId, success, error) {
    OPKP.batch([
        {
            name: "components",
            controller: "path",
            model: "fir_food>fir_value>fir_component",
            params: {
                q: "ORIGFDCD:'" + foodId + "'",
                columns: ["ORIGFDNM", "ORIGCPNM", "SELVAL"]
            },
            dependencies: ["food"]
        },
        {
            name: "food",
            controller: "path",
            model: "fir_food",
            params: {
                q: "ORIGFDCD:'" + foodId + "'"
            }
        }
    ], function (data) {
        var food = data["food"]["result"][0];

        food.components = data["components"]["result"];

        success(food);
    }, error);
};

OPKP.findFoods = function (keywords, offset, count, success, error) {
    if (!keywords || keywords.length == 0) {
        success(null);

        return;
    }

    keywords = keywords.join(",");

    var url = OPKP.host + "search/fir_food?columns=ORIGFDCD,ORIGFDNM,ENGFDNAM,SCINAM&keywords=" + keywords + "&limit=" + offset + "," + count;

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
};
