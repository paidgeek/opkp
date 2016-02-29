$(function () {
    var $foodProfileModal = $("#food-profile-modal");

    $foodProfileModal.focus();

    window.onFoodProfileClick = function(entityId) {
        $foodProfileModal.modal("show");
    }
});
