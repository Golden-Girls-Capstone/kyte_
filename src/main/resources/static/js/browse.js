document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('myModal');
    const profileCardsContainer = document.getElementById('profile-cards');
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');


    // Function to open the modal and set the pet's API ID
    function openModal(petData) {
    const closeModalButton = document.getElementById('closeModalButton');
    const confirmButton = document.getElementById('submit-foster-btn');
    const openModalButton = document.querySelectorAll('.openModalButton');
        modal.innerHTML = `
        <div  class="modal-content modal-dialog modal-dialog-centered modal-dialog-scrollable" >

        <span id="closeModalButton" class="close">&times;</span>
        <h2 class="modal-name"></h2>
        <img class="modal-image" src="${petData.photos[0].small}">
        <p class="modal-pet-info-browse">Name: ${petData.name}</p>
        <p class="modal-pet-info-browse">Age: ${petData.age}</p>
        <p class="modal-pet-info-browse">Gender: ${petData.gender}</p>
        <p class="modal-pet-info-browse">Age: ${petData.status}</p>


            <input type="hidden" id="petId" name="petId" value="${petData.id}">
            <input type="hidden" id="petName" name="petName" value="${petData.name}">
            <input type="hidden" id="petType" name="petType" value="${petData.type}">
            <input type="hidden" id="petBreed" name="petBreed" value="${petData.breeds[0]}">
            <input type="hidden" id="petAge" name="petAge" value="${petData.age}">
            <input type="hidden" id="petSize" name="petSize" value="${petData.size}">
            <input type="hidden" id="petPhoto" name="petPhoto" value="${petData.photos[0].small}">
            <input type="hidden" id="petGender" name="petGender" value="${petData.gender}">
            <input type="hidden" id="petStatus" name="petStatus" value="${petData.status}">
            <label for="start">Start Date: </label>
            <input type="text" id="start" name="startDate" placeholder="MM/DD/YYYY">
            <label for="end" >End Date: </label>
            <input type="text" id="end" name="endDate" placeholder="MM/DD/YYYY">
            <input id="submit-foster-btn" type="submit" value="Submit" name="submitFoster">
    </div>
        `
        modal.style.display = 'block';
        modal.querySelector('submit-foster-btn').addEventListener('click', async function() {
            const firstUrl = 'fillInHere'; // ******enter endpoint******
            const petObject = {
                petId: petData.id,
                petName: petData.name,
                petType: petData.type,
                petBreed: petData.breeds[0],
                petAge: petData.age,
                petSize: petData.size,
                petPhoto: petData.photos[0].small,
                petGender: petData.gender,
                petStatus: petData.status
            }
            const fetchOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify(petObject)
            }
            let results = await fetch(firstUrl, fetchOptions);



        })
    // closeModalButton.addEventListener('click', closeModal);
    // confirmButton.addEventListener('click', () => {
    //     // Add your code to handle the submission here
    //     alert('You confirmed!');
    //     closeModal();
    // });
    }

    // Function to close the modal
    function closeModal() {
        modal.style.display = 'none';
    }


    // Event delegation to handle click events for dynamically generated "Foster" buttons
    profileCardsContainer.addEventListener('click', function(event) {
        if (event.target.classList.contains('openModalButton')) {
            const petId = event.target.getAttribute('data-pet-id');
            openModal(petId);
        }
    });

    // Event listeners for opening and closing the modal
    // for (const card of profileCardsContainer.querySelectorAll('.openModalButton')) {
    //     card.addEventListener('click', function() {
    //         const petId = card.getAttribute('data-pet-id'); // Get the pet's API ID
    //         console.log('Clicked Foster button for pet ID:' + petId); // Add this line
    //         openModal(petId); // Pass the API ID to the openModal function
    //     });
    // }


    // Function to render search results as profile cards
    function renderSearchResults(data) {
        profileCardsContainer.innerHTML = ''; // Clear the container

        for (const animalKey in data) {
            if (data.hasOwnProperty(animalKey)) {
                const animals = data[animalKey];
                for (let i = 0; i < animals.length; i++) {
                    const petData = animals[i];
                    const card = document.createElement('div');
                    card.classList.add('profile-card');

                    let imageUrl = '/img/default.jpg'; // Default image

                    if (petData.photos && petData.photos.length > 0) {
                        // Use the URL of the first photo from the API
                        imageUrl = petData.photos[0].medium || petData.photos[0].medium || petData.photos[0].small;
                    }
                    card.innerHTML = `
                    <div class="profile-image">
                            <img src="${imageUrl}" alt="Pet Image">
                    </div>
                        <h2 class="pet-name">${petData.name}</h2>
                    <div class="pet-status">${petData.status}</div>
                    <div class="profile-actions">
<!--                        <form method="post" action="/browse">-->
                            <button type="submit" name="fosterButton" class="openModalButton">Foster</button>
<!--                        </form>-->
<!--                        <form method="post" action="/browse">-->
                            <button type="submit" name="button" value="save" class="save-btn">Save</button>
<!--                        </form>x-->
                    </div>
                    `;

                    card.querySelector('.openModalButton').addEventListener('click', function(e) {
                        console.log(petData);
                        openModal(petData);
                    });
                    profileCardsContainer.appendChild(card);

                }
            }
        }
    }

    // Function to handle the form submission
    document.getElementById('search-form').addEventListener("submit", function(e) {
        e.preventDefault(); // Prevent the default form submission
        fetchBySearch();
    });

    // Function to fetch and render search results
    function fetchBySearch() {
        const type = document.getElementById('type').value;
        const zipcode = document.getElementById('zipcode').value;
        const age = document.getElementById('age').value;
        const size = document.getElementById('size').value;


        fetch('/api/token', {
            method: 'GET',
            headers: {
                'Accept': 'text/plain',
            }
        })
            .then(response => response.text())
            .then(token => {
                const bearerToken = `Bearer ${token}`;
                // Include age and size in the API request
                const apiUrl = `/api/data/search?type=${type}&age=${age}&size=${size}&zipcode=${zipcode}&page=1`;

                return fetch(apiUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': bearerToken,
                        'Content-Type': 'application/json'
                    },
                });
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Render the search results as profile cards
                renderSearchResults(data);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    // Initial data fetch and render (if needed)
    // fetchDataAndRender(); // If you have an initial data fetch
});






//POTENTIAL JAVASCRIPT FOR PAGINATION

// let currentPage = 1; // Initial page
// const itemsPerPage = 10; // Number of items per page
//
// function fetchPage(page) {
//     // Make an API request with the desired page parameter
//     // Update your API endpoint and handling as needed
//     fetch(`/api/resource?page=${page}&per_page=${itemsPerPage}`)
//         .then((response) => response.json())
//         .then((data) => {
//             // Process and display data here
//         })
//         .catch((error) => {
//             console.error(error);
//         });
// }
//
// // Handle the "Next" button click
// document.getElementById('forward').addEventListener('click', () => {
//     currentPage++;
//     fetchPage(currentPage);
// });
//
// // Handle the "Previous" button click
// document.getElementById('backward').addEventListener('click', () => {
//     if (currentPage > 1) {
//         currentPage--;
//         fetchPage(currentPage);
//     }
// });
//
// // Initial API request
// fetchPage(currentPage);