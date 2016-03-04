$(function () {
    window.populateFoodTable = function (table, foods) {
        var html = "<thead><tr><th style='text-align: center'>...</th>";

        for (var prop in foods[0]) {
            html += "<th>" + prop + "</th>";
        }

        html += "</tr></thead><tbody>";

        for (var i = 0; i < foods.length; i++) {
            html += "<tr>";

            var obj = foods[i];

            // assume first column is foodId
            var foodId = obj[Object.keys(obj)[0]];

            html += "<td><button type=\"button\" class=\"btn btn-default\" style='font-size: 0.8em' aria-label=\"Left Align\" onclick=\"onFoodProfileClick('" + foodId + "')\">" +
                "<span class=\"glyphicon glyphicon-edit\" aria-hidden=\"true\"></span>" +
                "</button></td>";

            for (var prop in obj) {
                html += "<td>" + obj[prop] + "</td>";
            }

            html += "</tr>";
        }

        html += "</tbody>";

        table.html(html);
    }
});