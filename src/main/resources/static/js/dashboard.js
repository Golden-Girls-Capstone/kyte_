const favoritesBtn = document.getElementById("showFavorites");
const fostersBtn = document.getElementById("showFosters");
const reviewsBtn = document.getElementById("showReviews");

favoritesBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("favorites").classList.add("active");
});

fostersBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("fosters").classList.add("active");
    document.getElementById("Prev-fosters").classList.add("active");

});

reviewsBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("reviews").classList.add("active");
});

function hideAllCards() {
    const cards = document.querySelectorAll(".cards");
    cards.forEach(card => card.classList.remove("active"));
}