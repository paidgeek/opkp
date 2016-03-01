$(function () {
    var $pageContent = $("#page-content");
    var $pageTitle = $("#page-title");

    window.loadPage = function (name) {
        $pageContent.css("display", "none");
        $pageContent.load(name.toLowerCase() + ".html", function () {
            $pageTitle.text(name);

            $.getScript("js/" + name + ".js", function () {
                $pageContent.fadeIn(300);
            });
        });
    }

    loadPage("Explore");

    $(".nav-sidebar li").on("click", function (event) {
        $(".nav-sidebar li").removeClass("active");

        var $tab = $(this);
        $tab.addClass("active");

        loadPage($tab.text());

        return false;
    })
});
