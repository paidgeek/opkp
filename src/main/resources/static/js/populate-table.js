$(function () {
    window.populateFoodTable = function (table, rows) {
        var html = "<thead><tr><th style='text-align: center'>...</th>";

        for (var prop in rows[0]) {
            html += "<th>" + prop + "</th>";
        }

        html += "</tr></thead><tbody>";

        for (var i = 0; i < rows.length; i++) {
            html += "<tr>";

            var row = rows[i];

            // assume first column is foodId
            var foodId = row[Object.keys(row)[0]];

            html += "<td><button type=\"button\" class=\"btn btn-default\" style='font-size: 0.8em' aria-label=\"Left Align\" onclick=\"onFoodProfileClick('" + foodId + "')\">" +
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