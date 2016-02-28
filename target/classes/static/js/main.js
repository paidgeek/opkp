var $searchButton = $("#searchButton");
var $resultTable = $("#resultTable");

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
        html += "<th>" + prop + "</th>";
    }

    html += "</tr></thead><tbody>";

    /*
     <thead>
     <tr>
     <th>#</th>
     <th>Header</th>
     <th>Header</th>
     <th>Header</th>
     <th>Header</th>
     </tr>
     </thead>
     <tbody>
     <tr>
     <td>1,001</td>
     <td>Lorem</td>
     <td>ipsum</td>
     <td>dolor</td>
     <td>sit</td>
     </tr>
     </tbody>
     */

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

    OPKP.getAllFoods(function (data) {
        writeResult(data);

        $searchButton.button("reset");
    });
});
