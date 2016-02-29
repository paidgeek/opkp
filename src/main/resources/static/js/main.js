$(function () {
    var $findButton = $("#find-button");
    var $resultTable = $("#result-table");
    var $entriesSelect = $("#entries-select");

    $resultTable.fixedHeader();

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

        var html = "<thead><tr><th>...</th>";

        for (var prop in objects[0]) {
            html += "<th data-column-id='" + prop + "'>" + prop + "</th>";
        }

        html += "</tr></thead><tbody>";

        for (var i = 0; i < count; i++) {
            html += "<tr>";

            var obj = objects[i];

            // assume first column is entity-id
            var entityId = obj[Object.keys(obj)[0]];

            html += "<td><button type=\"button\" class=\"btn btn-default\" aria-label=\"Left Align\" onclick=\"onFoodProfileClick('" + entityId + "')\">" +
                "<span class=\"glyphicon glyphicon-align-left\" aria-hidden=\"true\"></span>" +
                "</button></td>";

            for (var prop in obj) {
                html += "<td>" + obj[prop] + "</td>";
            }

            html += "</tr>";
        }

        html += "</tbody>";

        $resultTable.html(html);
    }

    $findButton.click(function (e) {
        clearResult();
        $findButton.button("loading");

        var stmt = {};
        stmt.from = "fir_food";
        stmt.expr = "ORIGFDCD, ORIGFDNM, ENGFDNAM, SCINAM";

        var count = $entriesSelect.val();

        if (!count) {
            stmt.count = 100;
        } else if (count !== "All") {
            stmt.count = parseInt(count);
        }

        console.log(stmt);

        OPKP.select(stmt, function (data) {
            console.log(data);
            writeResult(data);

            $findButton.button("reset");
        }, function () {
            $findButton.button("reset");
        });
    });
});
