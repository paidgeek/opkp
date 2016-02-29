$(function () {
    var $foodProfileModal = $("#food-profile-modal");

    $foodProfileModal.focus();

    window.onFoodProfileClick = function (foodId) {
        OPKP.getFood(foodId, function (data) {
            ko.cleanNode(document.body);
            ko.applyBindings(data);

            $foodProfileModal.modal("show");
        }, function () {
            alert("error");
        });
    }
});
