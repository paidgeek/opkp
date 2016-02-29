$(function () {
    var $findButton = $("#find-button");
    var $searchField = $("#search-field");
    var $resultTable = $("#result-table");
    var $entriesSelect = $("#entries-select");

    $resultTable.fixedHeader();


    function findFoods() {
        $findButton.button("loading");

        var stmt = {};
        stmt.from = "fir_food";
        stmt.expr = "ORIGFDCD, ORIGFDNM, ENGFDNAM, SCINAM";

        var query = $searchField.val().trim();

        if (query) {
            stmt.where = "ORIGFDNM LIKE '" + query;
            stmt.where += "' OR ENGFDNAM LIKE '" + query;
            stmt.where += "' OR SCINAM LIKE '" + query + "'";
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

    $findButton.click(function (e) {
        findFoods();
    });
});
