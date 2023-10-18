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
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const editProfileForm = document.querySelector('.edit-profile-form');
const validationError = document.querySelector('.validation-error');
const editProfileModal = document.querySelector('.edit-profile-modal');


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

async function checkValidationError() {
    const validationErrorUrl = "/dashboard/send/validation/error";
    const validationErrorOptions = {
        method: 'GET',
        headers: {
            'Accept': 'text/plain',
            'X-CSRF-TOKEN': csrfToken
        }
    };
   try {
       let validationErrorResponse = await fetch(validationErrorUrl, validationErrorOptions);
       if (validationErrorResponse.ok) {
           const validationResponse = await validationErrorResponse.text();
           console.log(validationResponse === "validationError");
           if (validationResponse === "validationError") {
               console.log(validationError);
               validationError.innerHTML = `
                    <h2>Incorrect Password! Try again!</h2>
                    `;
               validationError.style.display = "block";
               setTimeout(function () {
                   console.log("In timeout");
                   validationError.style.display = "none";
                   e.preventDefault();
               }, 3000);
           } else {
               console.log("validationResponse is not 'validationError. Submitting form");
               validationError.innerHTML = '';
               editProfileForm.submit();
           }
       }else {
           console.log("validationErrorResponse is not ok. Submitting form");
           validationError.innerHTML = '';
           editProfileForm.submit();
       }
   } catch(error) {
       console.error("Error during validation request: ", error);
   }
}

});
