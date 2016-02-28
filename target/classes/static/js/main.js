$(document).ready(function () {
    $("#btn_search").click(function (e) {
        OPKP.getAllFoods(function (data) {
            console.log(data);
        });
    });
});
