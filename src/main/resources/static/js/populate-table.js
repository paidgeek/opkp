$(function () {
    window.populateFoodTable = function (table, rows) {
        var html = "<thead><tr><th>...</th>";

        for (var prop in rows[0]) {
            html += "<th data-column-id='" + prop + "'>" + prop + "</th>";
        }

        html += "</tr></thead><tbody>";

        for (var i = 0; i < rows.length; i++) {
            html += "<tr>";

            var row = rows[i];

            // assume first column is foodId
            var foodId = row[Object.keys(row)[0]];

            html += "<td><button type=\"button\" class=\"btn btn-default\" aria-label=\"Left Align\" onclick=\"onFoodProfileClick('" + foodId + "')\">" +
                "<span class=\"glyphicon glyphicon-edit\" aria-hidden=\"true\"></span>" +
                "</button></td>";

            for (var prop in row) {
                html += "<td>" + row[prop] + "</td>";
            }

            html += "</tr>";
        }

        html += "</tbody>";

        table.html(html);
    }
});