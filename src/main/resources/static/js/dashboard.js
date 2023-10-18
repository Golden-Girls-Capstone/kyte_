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


const favoriteContainer = document.querySelector(".dash-favorite-container");
const fosterContainer = document.querySelector(".dash-foster-container");
const reviewContainer = document.querySelector(".dash-review-container");

favoritesBtn.addEventListener("click", () => {

    favoriteContainer.classList.add("shown");
    favoriteContainer.classList.remove("hidden");

    fosterContainer.classList.add("hidden");
    fosterContainer.classList.remove("shown");

    reviewContainer.classList.add("hidden");
    reviewContainer.classList.remove("shown");
});

fostersBtn.addEventListener("click", () => {

    fosterContainer.classList.add("shown");
    fosterContainer.classList.remove("hidden");

    favoriteContainer.classList.add("hidden");
    favoriteContainer.classList.remove("shown");

    reviewContainer.classList.add("hidden");
    reviewContainer.classList.remove("shown");

});

reviewsBtn.addEventListener("click", () => {

    reviewContainer.classList.add("shown");
    reviewContainer.classList.remove("hidden");

    fosterContainer.classList.add("hidden");
    fosterContainer.classList.remove("shown");

    favoriteContainer.classList.add("hidden");
    favoriteContainer.classList.remove("shown");

});

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
