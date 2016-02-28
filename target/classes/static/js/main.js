var $searchButton = $("#searchButton");
var $resultTable = $("#resultTable");
var $entriesSelect = $("#entriesSelect");

function clearResult() {
    $resultTable.html("");
}

function writeResult(result) {
    clearResult();

    var count = result["count"];
    var objects = result["objects"];

    if (!count || count == 0) {
        return;
    }

    var html = "<thead><tr>";

    for (var prop in objects[0]) {
        html += "<th data-column-id='" + prop + "'>" + prop + "</th>";
    }

    html += "<th data-column-id=\"commands\" data-formatter=\"commands\" data-sortable=\"false\">Commands</th>";

    html += "</tr></thead><tbody>";

    for (var i = 0; i < count; i++) {
        html += "<tr>";

        var obj = objects[i];

        for (var prop in obj) {
            html += "<td>" + obj[prop] + "</td>";
        }

        html += "</tr>";
    }

    html += "</tbody>";

    $resultTable.html(html);
}

$searchButton.click(function (e) {
    clearResult();
    $searchButton.button("loading");

    var stmt = {};
    stmt.from = "fir_food";

    var count = $entriesSelect.val();

    if (count !== "All") {
        stmt.count = parseInt(count);
    }

    console.log(stmt);

    OPKP.select(stmt, function (data) {
        console.log(data);
        writeResult(data);

        $searchButton.button("reset");
    }, function () {
        $searchButton.button("reset");
    });
});
