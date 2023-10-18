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
const favoritesBtn = document.querySelector("#showFavorites");
const fostersBtn = document.querySelector("#showFosters");
const reviewsBtn = document.querySelector("#showReviews");

favoritesBtn.addEventListener("click", () => {
    hideAllCards();
    document.getElementById("favorites-cards").classList.add("active");
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
    const favoriteCards = document.querySelectorAll(".favorites-cards");
    const reviewCards = document.querySelectorAll(".reviews-cards");
    const previousCards = document.querySelectorAll(".previous-cards");
    const currentCards = document.querySelectorAll(".current-cards");

    favoriteCards.forEach(card => card.classList.remove("active"));
    reviewCards.forEach(card => card.classList.remove("active"));
    previousCards.forEach(card => card.classList.remove("active"));
    currentCards.forEach(card => card.classList.remove("active"));

}

document.addEventListener("DOMContentLoaded", function () {
    // Get the container element
    const previousFosterContainer = document.querySelector(".previous-foster-container");

    // Add an event listener to the container to prevent scrolling from affecting the parent page
    previousFosterContainer.addEventListener("wheel", function (e) {
        e.preventDefault();
        this.scrollTop += e.deltaY;
    });
});
