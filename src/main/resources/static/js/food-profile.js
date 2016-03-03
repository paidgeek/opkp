$(function () {
    var $foodProfileModal = $("#food-profile-modal");

    $foodProfileModal.focus();

    window.onFoodProfileClick = function (foodId) {
        OPKP.getFood(foodId, function (food) {
            console.log(food);

            ko.cleanNode(document.body);
            ko.applyBindings(food);

            $foodProfileModal.modal("show");
        }, function () {
            alert("error");
        });
    }
});
