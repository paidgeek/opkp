$(function () {
    var $pageContent = $("#page-content");

    window.loadPage = function (name) {
        $pageContent.css("display", "none");
        $pageContent.load(name + ".html", function () {
            $.getScript("js/" + name + ".js", function () {
                $pageContent.fadeIn(300);
            });
        });
    }

    loadPage("explore");

    $(".nav-sidebar li").on("click", function (event) {
        $(".nav-sidebar li").removeClass("active");

        var $tab = $(this);
        $tab.addClass("active");

        loadPage($tab.text().toLowerCase());

        return false;
    })
});
