/* scroll the page to the list of goods */
$("#price-range").click(function () {
    var destination = $(".product-tabs").offset().top;
    $("html, body").animate({
        scrollTop: destination
    }, 500);
});
