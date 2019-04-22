/* search form in header */
$('#searchbox').click(function () {
    if (this.value == ' ... хочу купить') this.value = '';
}).blur(function () {
    if (this.value == '') this.value = ' ... хочу купить';
})

/* display submenu in the current visible area (bottom alignment) */
function displayDropMenu() {
    var clientHeight = $(window).height() - 10;
    $('.menu-list-1').each(function () {
        $(this).mouseenter(function () {
            var dropMenu = $(this).children('.drop-menu');
            var dropMenuClientBottom = $(this).offset().top - $(window).scrollTop() + dropMenu.outerHeight();
            if (dropMenuClientBottom > clientHeight) {
                dropMenu.css("top", (clientHeight - dropMenuClientBottom) + "px");
            } else {
                dropMenu.css("top", "0");
            }
        });
        $(this).mouseleave(function () {
            $(this).children('.drop-menu').css("top", "-9999em");
        });
    });
};
displayDropMenu();
$(window).on('resize', function () {
    displayDropMenu();
});

/* display drop-down menu after click button "Catalog" in header panel */
var $dropMenu = $("#dropdown-menu");
if ($dropMenu.length) {
    var $menu = $("#menu");
    $dropMenu.click(function () {
        if ($menu.css("top") == "-9999px") {
            $menu.css({"top": "1px"});
            $(".under-rubricator-overlay").css({"display": "block"});
            $("#dropdown-menu-sign").removeClass("fa-angle-down").addClass("fa-angle-up");
        } else {
            $menu.css({"top": "-9999px"});
            $(".under-rubricator-overlay").css({"display": "none"});
            $("#dropdown-menu-sign").removeClass("fa-angle-up").addClass("fa-angle-down");
        }
    });

    /* hide drop-down menu if clicked outside */
    $(".under-rubricator-overlay, .header").click(function (event) {
        if (!$dropMenu.has(event.target).length && !$(event.target).is($dropMenu) ) {
            $menu.css({"top": "-9999px"});
            $(".under-rubricator-overlay").css({"display": "none"});
            $("#dropdown-menu-sign").removeClass("fa-angle-up").addClass("fa-angle-down");
        }
    })
}

/* "button Up" (Back To Top) */
var top_show = 1200;
var delay = 500;
$(document).ready(function () {
    $(window).scroll(function () {
        if ($(this).scrollTop() > top_show) $('#top').fadeIn();
        else $('#top').fadeOut();
    });
    $('#top').click(function () {
        $('body, html').animate({
            scrollTop: 0
        }, delay);
    });
});