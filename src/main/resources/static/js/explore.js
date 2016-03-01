$(function () {
    var $findButton = $("#find-button");
    var $searchField = $("#search-field");
    var $resultTable = $("#result-table");
    var $entriesSelect = $("#entries-select");
    var $queryBuilder = $("#query-builder");

    window.findFoods = function () {
        $findButton.button("loading");
        $resultTable.html("");

        var stmt = {};
        stmt.from = "fir_food";
        stmt.expr = "ORIGFDCD, ORIGFDNM, ENGFDNAM, SCINAM";

        var keywords = $searchField.val().trim().split(/ +/);

        if (keywords.length > 0) {
            keywords = keywords.join(" ");

            stmt.where = "MATCH (ORIGFDNM) AGAINST('" + keywords + "') OR ";
            stmt.where += "MATCH (ENGFDNAM) AGAINST('" + keywords + "') OR ";
            stmt.where += "MATCH (SCINAM) AGAINST('" + keywords + "') ";
        }

        var count = $entriesSelect.val();

        if (!count) {
            stmt.count = 100;
        } else {
            stmt.count = parseInt(count);
        }

        OPKP.select(stmt, function (data) {
            $findButton.button("reset");

            populateFoodTable($resultTable, data["objects"]);
        }, function () {
            $findButton.button("reset");
        });
    }

    $queryBuilder.queryBuilder({
        filters: [{
            id: "name",
            label: "Name",
            type: "string"
        }]
    });

    $findButton.click(function (e) {
        findFoods();
    });
});
