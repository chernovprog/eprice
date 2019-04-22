/* For banner */
document.addEventListener('DOMContentLoaded', function () {
    startBanner();
})

var bannerImgUrl = [
    "img/banner/banner_1.png",
    "img/banner/banner_2.jpg",
];

function bannerIndex() { return Math.round(Math.random() * (bannerImgUrl.length - 1)); }

function startBanner() {
    var banner = document.getElementById('main-banner');
    banner.style.backgroundImage = "url(" + bannerImgUrl[bannerIndex()] + ")";
    banner.style.backgroundRepeat = "no-repeat no-repeat";
    banner.style.backgroundPosition = "center";
    /*banner.style.backgroundSize = "100% 100%";*/
    setTimeout(startBanner, 20000);
}

