/* prev & next buttons pagination */
var $pages = $('a.pages:not(.last-page)');
for (var i = 0; i < $pages.length; i++) {
    if ($($pages[i]).hasClass("active")) {
        if (i == 0) {
            $('.prev').addClass('inactive');
            $('.next').attr('href', $($pages[i + 1]).attr('href'));
        } else if (i == $pages.length - 1) {
            $('.prev').attr('href', $($pages[i - 1]).attr('href'));
            $('.next').addClass('inactive');
        } else {
            $('.prev').attr('href', $($pages[i - 1]).attr('href'));
            $('.next').attr('href', $($pages[i + 1]).attr('href'));
        }
        break;
    }
}

