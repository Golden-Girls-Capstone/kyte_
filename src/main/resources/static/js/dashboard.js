// const favoritesBtn = document.getElementById("showFavorites");
// const fostersBtn = document.getElementById("showFosters");
// const reviewsBtn = document.getElementById("showReviews");
//
// favoritesBtn.addEventListener("click", () => {
//     hideAllCards();
//     document.getElementById("favorites").classList.add("active");
// });
//
// fostersBtn.addEventListener("click", () => {
//     hideAllCards();
//     document.getElementById("fosters").classList.add("active");
//     document.getElementById("Prev-fosters").classList.add("active");
//     document.getElementById('previousFostersHeader').style.display = 'block';
//
// });
//
// reviewsBtn.addEventListener("click", () => {
//     hideAllCards();
//     document.getElementById("reviews").classList.add("active");
// });
//
// function hideAllCards() {
//     const cards = document.querySelectorAll(".cards");
//     cards.forEach(card => card.classList.remove("active"));
// }
//
// // document.getElementById('showFosters').addEventListener('click', function () {
// //     // Show the "Previous Fosters" header when "Show Fosters" button is clicked
// //     document.getElementById('previousFostersHeader').style.display = 'block';
// // });
const favoritesBtn = document.getElementById("showFavorites");
const fostersBtn = document.getElementById("showFosters");
const reviewsBtn = document.getElementById("showReviews");

favoritesBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("favorites").classList.add("active");
    document.getElementById('previousFostersHeader').style.display = 'none'; // Hide the header
});

fostersBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("fosters").classList.add("active");
    document.getElementById("Prev-fosters").classList.add("active");
    document.getElementById('previousFostersHeader').style.display = 'block'; // Show the header
});

reviewsBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("reviews").classList.add("active");
    document.getElementById('previousFostersHeader').style.display = 'none'; // Hide the header
});

function hideAllCards() {
    const cards = document.querySelectorAll(".cards");
    cards.forEach(card => card.classList.remove("active"));
}
