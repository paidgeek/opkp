$(function () {
    var $findButton = $("#find-button");
    var $searchField = $("#search-field");
    var $resultTable = $("#result-table");
    var $entriesSelect = $("#entries-select");
    var $queryBuilder = $("#query-builder");

    window.findFoods = function () {
        $findButton.button("loading");
        $resultTable.html("");

        var keywords = $searchField.val().trim().split(/ +/);
        var count = $entriesSelect.val();

        if (!count) {
            count = 100;
        } else {
            count = parseInt(count);
        }

        OPKP.findFoods(keywords, 0, count, function (data) {
            $findButton.button("reset");

            populateFoodTable($resultTable, data);
        }, function () {
            $findButton.button("reset");
        })
    }

    /*
    $queryBuilder.queryBuilder({
        filters: [{
            id: "name",
            label: "Name",
            type: "string"
        }]
    });
    */

    $findButton.click(function (e) {
        findFoods();
    });
});
