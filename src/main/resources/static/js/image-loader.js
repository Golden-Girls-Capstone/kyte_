
window.onload = function () {
    hideLoader();
};

function hideLoader() {
    document.querySelector("#loading-ham").style.display = 'none';
    document.querySelector("body").classList.remove("loading");
}
